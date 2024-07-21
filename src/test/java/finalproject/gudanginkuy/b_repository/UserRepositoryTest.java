package finalproject.gudanginkuy.b_repository;

import finalproject.gudanginkuy.a_model.Category;
import finalproject.gudanginkuy.a_model.Role;
import finalproject.gudanginkuy.a_model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;
    private User user1;
    @BeforeEach
    void setUp(){
        user = User.builder()
                .id(1)
                .username("test")
                .password("test")
                .role(Role.ROLE_USER)
                .build();

        user1 = User.builder()
                .id(2)
                .username("test1")
                .password("test1")
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);
        userRepository.save(user1);
    }


    @Test
    public void FindByIdTest(){
        Optional<User> foundUser = userRepository.findById(user.getId());

        assertTrue(foundUser.isPresent(),"found");
        assertEquals(user,foundUser.get());
    }

    @Test
    public void getAll(){
        List<User> addUser = userRepository.findAll();

        assertThat(user).isNotNull();
        assertThat(addUser).hasSize(2);
    }
    @Test
    public void create(){
        User addUser = userRepository.findByUsername("test").orElseThrow();

        assertEquals(user,addUser);
    }
    @Test
    public void update(){
        User addUser= userRepository.findById(user.getId()).orElseThrow();
        addUser.setUsername("update");

        assertThat(addUser).isNotNull();
        assertThat(addUser.getUsername()).isEqualTo("update");
    }

    @Test
    public void delete(){
        userRepository.deleteById(user.getId());
        User delete = userRepository.findById(user.getId()).orElse(null);

        assertThat(delete).isNull();
    }

}
