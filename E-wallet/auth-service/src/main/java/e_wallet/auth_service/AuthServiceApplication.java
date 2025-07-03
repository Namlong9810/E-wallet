package e_wallet.auth_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import e_wallet.shared_module.config.PasswordEncodeConfig;

@SpringBootApplication(scanBasePackages = {"e_wallet.user_service", "e_wallet.auth_service", "e_wallet.shared_module"})
@Import(PasswordEncodeConfig.class)
@EntityScan(basePackages = "e_wallet.shared_module.entity")
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
