package e_wallet.shared_module.adapter;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsServiceAdapter {
    UserDetails loadUserByUsername(String username);
}
