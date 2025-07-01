package e_wallet.notification_service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public String sendNotification(String userId, String transactionId) {
        return "Giao dịch này bị đánh giá là gian lận. Vui lòng kiểm tra lại.";
    }
}
