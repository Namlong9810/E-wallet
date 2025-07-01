package e_wallet.auth_service.controller;

import e_wallet.dto.req.RegisterDTO;
import e_wallet.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/auth")
public class AuthController {
    @Autowired
    private UserService userService;

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
