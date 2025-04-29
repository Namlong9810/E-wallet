package e_wallet.transaction_service.dto.res;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WalletDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "wallet_id", unique = true)
    UUID walletId;

    @Column(name = "user_id", nullable = false)
    UUID userId;

    @Column(name = "balance")
    BigDecimal balance;
}