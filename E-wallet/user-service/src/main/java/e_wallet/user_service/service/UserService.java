package e_wallet.user_service.service;

import e_wallet.shared_module.config.PasswordEncodeConfig;
import e_wallet.shared_module.dto.req.RegisterDTO;
import e_wallet.shared_module.entity.User;
import e_wallet.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncodeConfig passwordEncoder;

    public void register(RegisterDTO registerDTO) {
        User user = User.builder()
                .email(registerDTO.getUserName())
                .password(passwordEncoder.passwordEncoder().encode(registerDTO.getPassword()))
                .build();

        userRepository.save(user);
    }

    public User findUserByUserID(UUID user_id){
        return userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("Can not found user"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
