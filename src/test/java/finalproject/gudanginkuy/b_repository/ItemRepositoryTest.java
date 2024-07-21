package finalproject.gudanginkuy.b_repository;

import finalproject.gudanginkuy.a_model.Item;
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
public class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;

    private Item item;
    private Item item1;

    @BeforeEach
    void setUp(){
    item = Item.builder()
            .name("test")
            .quantity(10)
            .barcode("1234")
            .build();
    item1 = Item.builder()
            .name("test1")
            .quantity(10)
            .barcode("1234")
            .build();
    itemRepository.save(item);
    itemRepository.save(item1);
    }
    @Test
    public void getAll(){
        List<Item> addItem = itemRepository.findAll();

        assertThat(addItem).isNotNull();
        assertThat(addItem).hasSize(2);
    }
    @Test
    public void getOne(){
        Item addItem = itemRepository.findById(item.getId()).orElseThrow(null);

        assertThat(addItem).isNotNull();
    }

    @Test
    public void create(){
        Item addItem = itemRepository.findById(item.getId()).orElseThrow();

        assertThat(addItem).isNotNull();
    }

    public void update(){
        Item addItem = itemRepository.findById(item.getId()).orElseThrow();
        addItem.setName("update");
        Item update = itemRepository.save(addItem);

        assertThat(update).isNotNull();
        assertThat(update.getName()).isEqualTo("update");
    }

    @Test
    public void delete(){
        itemRepository.deleteById(item.getId());
        Item delete = itemRepository.findById(item.getId()).orElse(null);

        assertThat(delete).isNull();

    }

}
