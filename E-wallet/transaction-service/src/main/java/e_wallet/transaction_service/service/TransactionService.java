package e_wallet.transaction_service.service;

import e_wallet.transaction_service.dto.req.TransactionReq;
import e_wallet.transaction_service.dto.res.WalletDTO;
import e_wallet.transaction_service.entity.Transaction;
import e_wallet.transaction_service.entity.TransactionType;
import e_wallet.transaction_service.repository.TransactionRepository;
import e_wallet.wallet_service.entity.Wallet;
import e_wallet.wallet_service.repository.WalletRepository;
import e_wallet.wallet_service.service.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final WalletService walletService;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public Transaction createTransaction(TransactionReq transactionReq, HttpServletRequest httpServletRequest){
        this.validateTransfer(transactionReq);

//        Thực hiện rút tiền khỏi ví
        Wallet sender = walletService.withdraw(transactionReq.getSender_id(), transactionReq.getAmount());
//        Thực hiện thêm tiền vào ví
        walletService.deposit(transactionReq.getReceiver_id(), transactionReq.getAmount());


        // Lấy địa chỉ ip thực hiện giao dịch
        String clientIP = getClientIp(httpServletRequest);

        Instant previousTransactionDate = transactionRepository.findPreviousTransactionDate(sender.getUserId(), sender.getWalletId());

        Instant periodTime = Instant.now().minus(Duration.ofMinutes(5));
        Integer frequency = transactionRepository.countTransactionIn5minByUserId(sender.getUserId(), periodTime);

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

        return transactionRepository.save(transaction);
    }

    private void validateTransfer(TransactionReq transactionReq) {
        if (transactionReq.getSender_id().equals(transactionReq.getReceiver_id())) {
            throw new RuntimeException("Wallet Sender and receiver cannot be the same");
        }

        Wallet sender = walletRepository.findById(transactionReq.getSender_id())
                .orElseThrow(() -> new RuntimeException("Sender wallet not found"));

        Wallet receiver = walletRepository.findById(transactionReq.getReceiver_id())
                .orElseThrow(() -> new RuntimeException("Receiver wallet not found"));

        if (sender.getBalance().compareTo(transactionReq.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
    }

    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For"); // nếu qua proxy/nginx
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];  // lấy IP đầu tiên trong chuỗi
    }
}
