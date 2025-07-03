package e_wallet.shared_module.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegisterDTO {
    @JsonProperty("username")
    private String userName;
    private String password;
}
