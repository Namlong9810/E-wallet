package e_wallet.user_service.service;

import e_wallet.dto.req.RegisterDTO;
import e_wallet.user_service.entity.User;
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

    public void register(RegisterDTO registerDTO) {
        User user = User.builder()
                .email(registerDTO.getUserName())
                .password(registerDTO.getPassword())
                .build();

        userRepository.save(user);
    }

//    public User findUser(String username) {
//        return userRepository.findByEmail(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//    }
//

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
