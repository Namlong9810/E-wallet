package e_wallet.notification_service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendNotification(String userId, String transactionId) {
        String message = String.format("Giao dịch %s cho người dùng %s", transactionId, userId);
    }
}
