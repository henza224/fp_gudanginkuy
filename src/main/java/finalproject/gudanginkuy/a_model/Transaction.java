package finalproject.gudanginkuy.a_model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Item item;

    private Integer quantity;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh-mm")
    private LocalDateTime timestamp;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
}
