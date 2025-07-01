package e_wallet.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RegisterDTO {
    private String userName;
    private String password;
}
