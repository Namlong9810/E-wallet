package e_wallet.transaction_service.service;

import e_wallet.fraud_detection_service.fraud_service.FraudService;
import e_wallet.notification_service.NotificationService;
import e_wallet.transaction_service.exception.FraudDetectedException;
import e_wallet.transaction_service.repository.TransactionRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.dto.req.TransactionReq;
import org.example.dto.req.WalletReq;
import org.example.dto.res.WalletDTO;
import org.example.entity.Transaction;
import org.example.entity.TransactionType;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final RestTemplate restTemplate;
    private final TransactionRepository transactionRepository;
//    private final FraudService fraudService;
//    private final NotificationService notificationService;

    @Transactional
    public Transaction createTransaction(TransactionReq transactionReq, HttpServletRequest httpServletRequest) {
        this.validateTransfer(transactionReq);

        // Gọi API ví để rút tiền
        WalletDTO sender = withdraw(transactionReq.getSender_id(), transactionReq.getAmount());

        // Gọi API ví để nạp tiền
        deposit(transactionReq.getReceiver_id(), transactionReq.getAmount());

        // Lấy địa chỉ IP
        String clientIP = getClientIp(httpServletRequest);

        // Lấy thông tin giao dịch trước đó và tần suất giao dịch
        Instant previousTransactionDate = transactionRepository.findPreviousTransactionDate(sender.getUserId(), sender.getWalletId());
        Instant periodTime = Instant.now().minus(Duration.ofMinutes(5));
        Integer frequency = transactionRepository.countTransactionIn5minByUserId(sender.getUserId(), periodTime);

        // Tạo bản ghi transaction
        Transaction transaction = Transaction.builder()
                .userId(sender.getUserId())
                .walletId(sender.getWalletId())
                .amount(transactionReq.getAmount())
                .transaction_type(TransactionType.DEBIT)
                .ip_address(clientIP)
                .frequency(frequency)
                .balance(sender.getBalance())
                .previous_transaction_date(previousTransactionDate)
                .build();

//        boolean isFraud = fraudService.checkForFraud(transaction);
//
//        //Kiểm trả kết quả trả về
//        if (isFraud) {
//            notificationService.sendNotification(sender.getUserId().toString(), transaction.getTransactionId().toString());
//
//            throw new FraudDetectedException("Giao dịch bị phát hiện là gian lận.");
//        }

        return transactionRepository.save(transaction);
    }

    private void validateTransfer(TransactionReq transactionReq) {
        if (transactionReq.getSender_id().equals(transactionReq.getReceiver_id())) {
            throw new RuntimeException("Wallet Sender and receiver cannot be the same");
        }

        // NOTE: Nếu không có DB riêng để kiểm tra, bạn có thể gọi API khác bên WalletService
        // để kiểm tra số dư trước khi gọi withdraw
    }

    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    // Gọi API withdraw
    private WalletDTO withdraw(UUID walletId, BigDecimal amount) {
        String url = "http://localhost:8082/user/wallet/withdraw";

        WalletReq req = new WalletReq();
        req.setWallet_id(walletId);
        req.setAmount(amount);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<WalletReq> entity = new HttpEntity<>(req, headers);
        ResponseEntity<WalletDTO> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, entity, WalletDTO.class);
        return responseEntity.getBody();
    }


    // Gọi API deposit
    private WalletDTO deposit(UUID walletId, BigDecimal amount) {
        String url = "http://localhost:8082/user/wallet/deposit";

        WalletReq req = new WalletReq();
        req.setWallet_id(walletId);
        req.setAmount(amount);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<WalletReq> entity = new HttpEntity<>(req, headers);
        ResponseEntity<WalletDTO> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, entity, WalletDTO.class);
        return responseEntity.getBody();
    }

}
