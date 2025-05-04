package e_wallet.notification_service;

public class NotificationService {

    public void sendNotification(String userId, String transactionId) {
        String message = String.format("Giao dịch %s cho người dùng %s", transactionId, userId);
    }
}
