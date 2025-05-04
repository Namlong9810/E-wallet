package e_wallet.fraud_detection_service.fraud_service;

import org.example.entity.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Value;

@Service
public class FraudService {

    @Value("${fraud.api.url}") // URL API Python (Flask)
    private String fraudApiUrl;

    private final RestTemplate restTemplate;

    public FraudService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean checkForFraud(Transaction transaction) {
        // Chuyển đổi dữ liệu của transaction thành request payload
        FraudRequest request = new FraudRequest();
        request.setAmount(transaction.getAmount().doubleValue());
        request.setFrequency(transaction.getFrequency());
        // Thêm các đặc trưng khác nếu cần từ transaction

        // Gửi dữ liệu đến API Python
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<FraudRequest> entity = new HttpEntity<>(request, headers);

        // Gọi API Python để kiểm tra gian lận
        try {
            ResponseEntity<FraudResponse> response = restTemplate.exchange(fraudApiUrl + "/check-fraud",
                    HttpMethod.POST, entity, FraudResponse.class);

            // Kiểm tra nếu response không phải null và có body
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody().getIsFraud() == 1;
            } else {
                // Log an error or handle the case when API does not return a valid response
                throw new IllegalStateException("API response is invalid or not OK");
            }
        } catch (Exception e) {
            // Log exception and handle errors (e.g., network issues, timeout, etc.)
            throw new RuntimeException("Error calling fraud detection service", e);
        }
    }

    // Lớp Request (dữ liệu gửi đi)
    public static class FraudRequest {
        private double amount;
        private int frequency;

        // Getter và setter
        public double getAmount() { return amount; }
        public void setAmount(double amount) { this.amount = amount; }

        public int getFrequency() { return frequency; }
        public void setFrequency(int frequency) { this.frequency = frequency; }
    }

    // Lớp Response (dữ liệu nhận từ API Python)
    public static class FraudResponse {
        private int isFraud;

        public int getIsFraud() { return isFraud; }
        public void setIsFraud(int isFraud) { this.isFraud = isFraud; }
    }
}

