package finalproject.gudanginkuy.utils.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {
    private Integer itemId;
    private Integer quantity;
}
