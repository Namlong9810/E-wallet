package e_wallet.auth_service.config;

import e_wallet.shared_module.adapter.UserDetailsServiceAdapter;
import e_wallet.shared_module.config.JwtFilter;
import e_wallet.shared_module.config.JwtTokenProvider;
import e_wallet.shared_module.config.PasswordEncodeConfig;
import e_wallet.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration("AuthSecurityConfig")
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncodeConfig passwordEncodeConfig;
    private final UserService userService;
    private final JwtTokenProvider jwt;
    private final UserDetailsServiceAdapter adapter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        JwtFilter jwtFilter = new JwtFilter(jwt, adapter);
        return http
                .csrf(AbstractHttpConfigurer::disable)      //Disable csrf
                .sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers("/user/auth/**").permitAll()
                        .anyRequest().authenticated()     //Cho phép gọi toan bo api ma khong can authen
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)
                .passwordEncoder(passwordEncodeConfig.passwordEncoder())
                .and()
                .build();
    }
}
