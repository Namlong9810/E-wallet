package e_wallet.user_service.controller;

import e_wallet.shared_module.dto.res.UserResponse;
import e_wallet.shared_module.entity.User;
import e_wallet.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        User user = (User) userService.loadUserByUsername(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserResponse response = new UserResponse(
                user.getUsername(),
                user.getPassword()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user_id/{user_id}")
    public ResponseEntity<?> findUserByUserId(@PathVariable String user_id) {
        UUID id = UUID.fromString(user_id);
        User user = (User) userService.findUserByUserID(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }
}

