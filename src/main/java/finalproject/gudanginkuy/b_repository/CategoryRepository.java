package finalproject.gudanginkuy.b_repository;

import finalproject.gudanginkuy.a_model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends
        JpaRepository<Category, Integer> {
}
