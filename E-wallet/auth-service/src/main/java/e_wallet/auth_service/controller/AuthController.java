package e_wallet.auth_service.controller;

import e_wallet.shared_module.config.JwtTokenProvider;
import e_wallet.shared_module.dto.req.LoginRequest;
import e_wallet.shared_module.dto.req.RegisterDTO;
import e_wallet.shared_module.entity.User;
import e_wallet.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );  // Method xác thực tại khoản nguời dùng

        User user = (User) userService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        try {
            userService.register(registerDTO);
        }catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        return ResponseEntity.ok("Register successfully!!");
    }
}
