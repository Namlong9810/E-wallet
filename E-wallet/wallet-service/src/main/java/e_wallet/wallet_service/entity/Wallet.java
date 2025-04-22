package e_wallet.wallet_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "wallet")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "wallet_id", unique = true)
    UUID wallet_id;

    @Column(name = "user_id", nullable = false, unique = true)
    UUID user_id;

    @Column(name = "balance")
    BigDecimal balance;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    Instant create_at;

    @UpdateTimestamp
    @Column(name = "updated_at")
    Instant updated_at;
}
