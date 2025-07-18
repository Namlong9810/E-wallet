package e_wallet.shared_module.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "wallet_transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "transaction_id", unique = true, nullable = false)
    private UUID transactionId;

    @Column(name = "user_id", unique = false, nullable = false)
    private UUID userId;

    @Column(name = "wallet_id", unique = false, nullable = false)
    private UUID walletId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "transaction_date")
    private Instant transaction_date;

    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    private TransactionType transaction_type;

    @Column(name = "ip_address")
    private String ip_address;

    @Column(name = "frequency")
    private Integer frequency;

    @Column(name = "previous_transaction_date", updatable = false)
    private Instant previous_transaction_date;

    @Column(name = "balance")
    private BigDecimal balance;
}
