package e_wallet.auth_service.config;

import e_wallet.shared_module.adapter.UserDetailsServiceAdapter;
import e_wallet.shared_module.config.JwtFilter;
import e_wallet.shared_module.config.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtFilterConfig {
    @Bean
    public JwtFilter jwtFilter(JwtTokenProvider jwtTokenProvider, UserDetailsServiceAdapter adapter){
        return new JwtFilter(jwtTokenProvider, adapter);
    }
}
