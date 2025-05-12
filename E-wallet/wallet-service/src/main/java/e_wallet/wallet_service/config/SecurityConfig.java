package e_wallet.wallet_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration("walletSecurityConfig")
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/user/wallet/**")
                .csrf(AbstractHttpConfigurer::disable) // disable CSRF
                .authorizeHttpRequests( auth -> auth
                    .requestMatchers("user/transaction/*").permitAll()
                    .anyRequest().permitAll()
                );
        return http.build();
    }
}
