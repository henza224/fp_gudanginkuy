package finalproject.gudanginkuy.utils.dto;


import finalproject.gudanginkuy.a_model.TransactionType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDTO {
    private Integer user_id;

    private Integer item_id;

    private Integer quantity;

    private LocalDate date;
}
