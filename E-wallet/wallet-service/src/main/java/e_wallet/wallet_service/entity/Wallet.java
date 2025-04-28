package e_wallet.wallet_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "wallet")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "wallet_id", unique = true)
    UUID walletId;

    @Column(name = "user_id", nullable = false)
    UUID userId;

    @Column(name = "balance")
    BigDecimal balance;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    Instant create_at;

    @UpdateTimestamp
    @Column(name = "updated_at")
    Instant updated_at;

    Wallet(UUID userId, BigDecimal balance){
        this.userId = userId;
        this.balance = balance;
    }
}
