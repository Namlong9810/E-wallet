package e_wallet.auth_service.controller;

import e_wallet.auth_service.service.JwtService;
import e_wallet.dto.req.LoginRequest;
import e_wallet.dto.req.RegisterDTO;
import e_wallet.user_service.entity.User;
import e_wallet.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/auth")
public class AuthController {
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );  // Method xác thực tại khoản nguời dùng

        User user = userService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok("oke");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(RegisterDTO registerDTO) {
        try {
            userService.register(registerDTO);
        }catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        return ResponseEntity.ok("Register successfully!!");
    }
}
