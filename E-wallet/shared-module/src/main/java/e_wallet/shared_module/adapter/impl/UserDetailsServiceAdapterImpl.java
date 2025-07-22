package e_wallet.shared_module.adapter.impl;

import e_wallet.shared_module.adapter.UserDetailsServiceAdapter;
import e_wallet.shared_module.dto.res.UserResponse;
import e_wallet.shared_module.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceAdapterImpl implements UserDetailsServiceAdapter {
    @Value("http://localhost:8081/user")
    private String userUrl;

    private final RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            String url = userUrl + "/" + username;
            UserResponse user = restTemplate.getForObject(url, UserResponse.class);

            log.info("Checking user data {}", user);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }

            return new User(
                    user.getUsername(),
                    user.getPassword()
            );
        } catch (Exception e) {
            throw new UsernameNotFoundException("Cannot load user: " + e.getMessage());
        }
    }
}
