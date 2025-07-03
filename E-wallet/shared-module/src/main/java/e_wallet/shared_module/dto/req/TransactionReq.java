package e_wallet.shared_module.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TransactionReq {
//    id ví của người dùng, gọi đến wallet để lấy thêm id người dùng
//    @JsonProperty("sender_id")
    public UUID sender_id;

//    @JsonProperty("sender_id")
    public UUID receiver_id;
    public BigDecimal amount;
}
