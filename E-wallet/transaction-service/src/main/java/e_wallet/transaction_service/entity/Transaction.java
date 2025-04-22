package e_wallet.transaction_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "Transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "transaction_id", unique = true, nullable = false)
    private UUID transaction_id;

    @Column(name = "user_id", unique = true, nullable = false)
    private UUID user_id;

    @Column(name = "wallet_id", unique = true, nullable = false)
    private UUID wallet_id;

    @Column(name = "amount")
    private BigDecimal amount;

    @CreationTimestamp
    @Column(name = "transaction_date", updatable = false)
    private Instant transaction_date;

    @Column(name = "transaction_type")
    private TransactionType transaction_type;

    @Column(name = "ip_address")
    private String ip_address;

    @Column(name = "frequency")
    private Integer frequency;

    @Column(name = "transaction_duration")
    private BigDecimal transaction_duration;

    @Column(name = "previous_transaction_date", updatable = false)
    private Instant previous_transaction_date;

    @Column(name = "balance")
    private BigDecimal balance;
}
