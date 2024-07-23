package finalproject.gudanginkuy.utils.dto;

import finalproject.gudanginkuy.a_model.TransactionType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionSummaryDTO {
    private TransactionType type;
    private Integer quantity;
}
