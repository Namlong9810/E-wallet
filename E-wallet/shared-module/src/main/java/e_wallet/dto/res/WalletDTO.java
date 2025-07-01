package e_wallet.dto.res;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WalletDTO {
    UUID walletId;

    UUID userId;

    String walletName;

    BigDecimal balance;
}