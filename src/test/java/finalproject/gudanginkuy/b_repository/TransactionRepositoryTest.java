package finalproject.gudanginkuy.b_repository;
import finalproject.gudanginkuy.a_model.Item;
import finalproject.gudanginkuy.a_model.Transaction;
import finalproject.gudanginkuy.a_model.TransactionType;
import finalproject.gudanginkuy.a_model.User;
import finalproject.gudanginkuy.c_service.impl.ItemServiceImpl;
import finalproject.gudanginkuy.c_service.impl.UserServiceImpl;
import finalproject.gudanginkuy.utils.dto.ItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TransactionRepositoryTest {
    @Autowired
    private TransactionRepository repository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;

    private Transaction transaction;
    private Transaction transaction1;
    private Item item;
    private Item item1;
    private User user;
    private User user1;

    @BeforeEach
    void setUp(){
        item= Item.builder()
                .name("test item1")
                .build();
        user = User.builder()
                .username("test username1")
                .build();

        item1 = Item.builder()
                .name("test item")
                .build();
        user1 = User.builder()
                .username("test username")
                .build();

        transaction = Transaction.builder()
                .item(item).quantity(10).user(user).type(TransactionType.IN)
                .build();

        transaction1 = Transaction.builder()
                .item(item1).quantity(10).user(user1).type(TransactionType.IN)
                .build();

        repository.save(transaction);
        repository.save(transaction1);
        userRepository.save(user);
        userRepository.save(user1);
        itemRepository.save(item1);
        itemRepository.save(item);
    }

    @Test
    public void getAll(){
        List<Transaction> add = repository.findAll();
        assertThat(add).isNotNull();
        assertThat(add).hasSize(2);
    }

    @Test
    public void getOne(){
        Transaction add = repository.findById(transaction.getId()).orElseThrow();
        assertThat(add).isNotNull();
    }
    @Test
    public void create(){
        Transaction add = repository.findById(transaction.getId()).orElseThrow();
        assertThat(add).isNotNull();
        assertThat(add.getId()).isEqualTo(1);
    }

    @Test
    public void update(){
        Transaction add = repository.findById(transaction.getId()).orElseThrow();
        add.setQuantity(10);
        repository.save(add);
        assertThat(add.getQuantity()).isEqualTo(10);
    }
    @Test
    public void delete(){
        repository.deleteById(transaction.getId());
        Transaction delete = repository.findById(transaction.getId()).orElse(null);
        assertThat(delete).isNull();
    }
}
