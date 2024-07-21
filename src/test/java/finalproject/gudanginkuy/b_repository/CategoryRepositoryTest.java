package finalproject.gudanginkuy.b_repository;


import finalproject.gudanginkuy.a_model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CategoryRepositoryTest {
        @Autowired
        private CategoryRepository categoryRepository;

        @Test
        public void Create() {
            Category category = Category.builder()
                    .id(1)
                    .name("Minuman")
                    .build();
            categoryRepository.save(category);

            Category foundCategory = categoryRepository.findById(category.getId()).orElse(null);

            assertEquals(category,foundCategory);


        }

        @Test
        public void GetAll() {
            Category category =Category.builder()
                    .id(1)
                    .name("category")
                    .build();
            Category category1 = Category.builder()
                    .name("customer 1")
                    .build();
            categoryRepository.save(category);
            categoryRepository.save(category1);

            List<Category> categories = categoryRepository.findAll();

            assertThat(category).isNotNull();
            assertThat(categories).hasSize(2);
        }

        @Test
        public void GetById() {
            Category category = Category.builder()
                    .name("category 1")
                    .build();
            categoryRepository.save(category);

            Category category1 = categoryRepository.findById(category.getId()).orElse(null);

            assertThat(category1).isNotNull();
        }

        @Test
        public void update() {
            Category category = Category.builder()
                    .id(1)
                    .name("test")
                    .build();
            categoryRepository.save(category);

            Category foundCategory = categoryRepository.findById(category.getId()).orElseThrow();
            foundCategory.setName("updated");
            Category updated = categoryRepository.save(foundCategory);

            assertThat(updated).isNotNull();
            assertThat(updated.getName()).isEqualTo("updated");
        }

        @Test
        public void Delete() {
            Category category = Category.builder()
                    .name("test")
                    .build();
            categoryRepository.save(category);

            categoryRepository.deleteById(category.getId());
            Category foundCategory = categoryRepository.findById(category.getId()).orElse(null);

            assertThat(foundCategory).isNull();
        }
    }

