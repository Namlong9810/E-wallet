package e_wallet.fraud_detection_service.fraud_service;

import org.example.dto.req.FraudRequest;
import org.example.dto.res.FraudResponse;
import org.example.entity.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Value;

@Service
public class FraudService {

    @Value("http://10.242.30.53:5000") // URL API Python (Flask)
    private String fraudApiUrl;

    private final RestTemplate restTemplate;

    public FraudService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean checkForFraud(Transaction transaction) {
        // Tạo request payload đưa sang api python
        FraudRequest request = new FraudRequest();
        request.setTransactionId(transaction.getTransactionId());
        request.setUserId(transaction.getUserId());
        request.setWalletId(transaction.getWalletId());
        request.setAmount(transaction.getAmount());
        request.setTransaction_date(transaction.getTransaction_date());
        request.setTransaction_type(transaction.getTransaction_type());
        request.setIp_address(transaction.getIp_address());
        request.setFrequency(transaction.getFrequency());
        request.setTransaction_duration(transaction.getTransaction_duration());
        request.setPrevious_transaction_date(transaction.getPrevious_transaction_date());
        request.setBalance(transaction.getBalance());


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
                // Log lỗi response
                throw new IllegalStateException("API response is invalid or not OK");
            }
        } catch (Exception e) {
            // Lỗi khác
            throw new RuntimeException("Error calling fraud detection service", e);
        }
    }
}

