package e_wallet.notification_service;

import org.example.dto.res.Notification;
import org.example.dto.res.common.ResponseObject;
import org.springframework.boot.reactor.ReactorEnvironmentPostProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class NotificationService {

    public String sendNotification(String userId, String transactionId) {
        return String.format("Giao dịch %s cho người dùng %s bị đánh giá là gian lận", transactionId, userId);
    }
}
