package e_wallet.shared_module.dto.req;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
