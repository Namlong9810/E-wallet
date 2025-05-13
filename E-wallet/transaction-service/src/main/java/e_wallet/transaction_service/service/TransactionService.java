package e_wallet.transaction_service.service;

import e_wallet.fraud_detection_service.fraud_service.FraudService;
import e_wallet.notification_service.NotificationService;
import e_wallet.transaction_service.exception.FraudDetectedException;
import e_wallet.transaction_service.repository.TransactionRepository;
import e_wallet.wallet_service.repository.WalletRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.dto.req.TransactionReq;
import org.example.dto.req.WalletReq;
import org.example.dto.res.WalletDTO;
import org.example.dto.res.common.ResponseObject;
import org.example.entity.Transaction;
import org.example.entity.TransactionType;
import org.example.entity.Wallet;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final RestTemplate restTemplate;
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final FraudService fraudService;
    private final NotificationService notificationService;

    @Transactional
    public ResponseObject<Transaction> createTransaction(TransactionReq transactionReq, HttpServletRequest httpServletRequest) {
        Instant start = Instant.now();
        this.validateTransfer(transactionReq);

        // Lấy thông tin ví sender, receiver
        Wallet sender = walletRepository.findById(transactionReq.getSender_id()).orElseThrow(()-> new IllegalArgumentException("Không tìm thấy " +
                "senderId"));

        walletRepository.findById(transactionReq.getReceiver_id()).orElseThrow(() -> new IllegalArgumentException("Không tìm " +
                "thấy receiverId"));

        // Lấy địa chỉ IP
        String clientIP = getClientIp(httpServletRequest);

        // Lấy thông tin giao dịch trước đó và tần suất giao dịch
        Instant previousTransactionDate = transactionRepository.findPreviousTransactionDate(sender.getUserId(), sender.getWalletId());
        Instant periodTime = Instant.now().minus(Duration.ofMinutes(5));
        Integer frequency = transactionRepository.countTransactionIn5minByUserId(sender.getUserId(), periodTime);

        BigDecimal transactionDuration = BigDecimal.valueOf(Duration.between(start, Instant.now()).toSecondsPart());
        // Tạo bản ghi transaction
        Transaction transaction = Transaction.builder()
                .userId(sender.getUserId())
                .walletId(sender.getWalletId())
                .amount(transactionReq.getAmount())
                .transaction_date(Instant.now())
                .transaction_type(TransactionType.DEBIT)
                .ip_address(clientIP)
                .frequency(frequency)
                .transaction_duration(transactionDuration)
                .balance(sender.getBalance())
                .previous_transaction_date(previousTransactionDate)
                .build();

//        boolean isFraud = clientIP != null;
        Transaction saved = transactionRepository.save(transaction);
        boolean isFraud = fraudService.checkForFraud(transaction);

//        Kiểm trả kết quả trả về
        if (isFraud) {
            String message = notificationService.sendNotification(sender.getUserId().toString(), transaction.getTransactionId().toString());
            throw new FraudDetectedException(message);
        }

        // Gọi API ví để rút tiền
        WalletDTO senderResult = withdraw(transactionReq.getSender_id(), transactionReq.getAmount());

        // Gọi API ví để nạp tiền
        WalletDTO receiverResult = deposit(transactionReq.getReceiver_id(), transactionReq.getAmount());

        if(senderResult == null || receiverResult == null){
            throw new IllegalArgumentException("Có lỗi xảy ra trong quá trình giao dịch!!");
        }

        return new ResponseObject<Transaction>("Khởi tạo giao dịch thành công", HttpStatus.OK.value(), LocalDateTime.now());
    }

    public Transaction getInforTransaction(UUID sender_id){
        return transactionRepository.findById(sender_id).orElseThrow(()-> new IllegalArgumentException("Không tìm thấy mã ví"));
    }

    public List<Transaction> getListTransactionById(UUID wallet_id){
        return transactionRepository.findAllByWalletId(wallet_id);
    }

    private void validateTransfer(TransactionReq transactionReq) {
        if (transactionReq.getSender_id().equals(transactionReq.getReceiver_id())) {
            throw new RuntimeException("Wallet id không trùng nhau");
        }
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
