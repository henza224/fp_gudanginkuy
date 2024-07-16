package finalproject.gudanginkuy.a_model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "stocks")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer quantity;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
}

