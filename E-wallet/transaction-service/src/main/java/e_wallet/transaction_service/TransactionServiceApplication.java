package e_wallet.transaction_service;

import jakarta.persistence.Entity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"e_wallet.notification_service", "org.example.config", "e_wallet.transaction_service", "e_wallet" +
		".fraud_detection_service", "e_wallet.wallet_service"})
@EntityScan(basePackages = "org.example.entity")
public class TransactionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionServiceApplication.class, args);
	}

}
