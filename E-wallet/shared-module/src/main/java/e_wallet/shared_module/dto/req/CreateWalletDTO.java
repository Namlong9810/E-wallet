package e_wallet.shared_module.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateWalletDTO {
    private UUID user_id;
    private String walletName;
    private BigDecimal balance;
}
