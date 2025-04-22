package e_wallet.transaction_service.entity;

import e_wallet.transaction_service.Enum.TransactionType;
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
@Table(name = "transaction")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "transaction_id", unique = true)
    UUID transaction_id;

    @Column(name = "wallet_id")
    UUID wallet_id;

    @Column(name = "amount")
    BigDecimal amount;

    @Column(name = "transaction_type")
    TransactionType transaction_type;

    @Column(name = "ip_address", length = 25)
    String ip_address;

    @Column(name = "transaction_duration")
    Integer transaction_duration;

    @Column(name = "previous_transaction")
    Instant previous_transaction;

    @CreationTimestamp
    @Column(name = "transaction_date")
    Instant transaction_date;

    @UpdateTimestamp
    @Column(name = "updated_at")
    Instant updated_at;
}
