package e_wallet.dto.req;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
