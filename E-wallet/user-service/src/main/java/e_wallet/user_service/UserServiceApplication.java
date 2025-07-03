package e_wallet.user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import e_wallet.shared_module.config.PasswordEncodeConfig;

@SpringBootApplication
@Import(PasswordEncodeConfig.class)
@EntityScan(basePackages = "e_wallet.shared_module.entity")
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
