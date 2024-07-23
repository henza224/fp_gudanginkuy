package finalproject.gudanginkuy.b_repository;

import finalproject.gudanginkuy.a_model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends
        JpaRepository<Category, Integer>{
    boolean existsByCategoryName(String categoryName);
}
