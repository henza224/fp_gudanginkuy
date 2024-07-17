package finalproject.gudanginkuy.utils.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDTO {
    private Integer barcode;
    private String name;
    private Integer quantity;
    private Integer vendor_id;
    private Integer category_id;
}