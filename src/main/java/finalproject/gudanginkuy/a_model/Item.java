package finalproject.gudanginkuy.a_model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Clob;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer barcode;

    private String name;

    private Integer quantity;

    private Clob pictureUrl;

    @OneToMany
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @OneToMany
    @JoinColumn(name = "category_id")
    private Category category;
}
