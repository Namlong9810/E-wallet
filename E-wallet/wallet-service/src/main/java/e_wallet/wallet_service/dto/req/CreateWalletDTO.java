package e_wallet.wallet_service.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.security.PublicKey;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateWalletDTO {
    private UUID user_id;
    private BigDecimal balance;
}
