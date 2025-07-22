package e_wallet.wallet_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"e_wallet.shared_module", "e_wallet.wallet_service", "e_wallet.user_service.service"})
@EnableJpaRepositories(basePackages = {"e_wallet.user_service.repository", "e_wallet.wallet_service.repository"})
@EntityScan(basePackages = "e_wallet.shared_module.entity")
public class WalletServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletServiceApplication.class, args);
	}

}
