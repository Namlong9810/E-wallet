package e_wallet.user_service.config;

import e_wallet.shared_module.adapter.UserDetailsServiceAdapter;
import e_wallet.shared_module.config.JwtFilter;
import e_wallet.shared_module.config.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration("userSecurityConfig")
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwt;
    private final UserDetailsServiceAdapter adapter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)      //Disable csrf
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers("/user/**").permitAll()
                        .anyRequest().authenticated()       //Cho phép gọi các request nhưng cần auth
                )
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(jwt, adapter);
    }
}
