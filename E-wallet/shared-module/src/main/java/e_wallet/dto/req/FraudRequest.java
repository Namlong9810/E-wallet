package e_wallet.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import e_wallet.entity.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FraudRequest {
    private UUID transactionId;

    private UUID userId;

    private UUID walletId;

    private BigDecimal amount;

    private Instant transaction_date;

    private TransactionType transaction_type;

    private String ip_address;

    private Integer frequency;

    private BigDecimal transaction_duration;

    private Instant previous_transaction_date;

    private BigDecimal balance;
}
