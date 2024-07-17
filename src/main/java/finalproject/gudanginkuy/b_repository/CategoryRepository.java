package finalproject.gudanginkuy.b_repository;

import finalproject.gudanginkuy.a_model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends
        JpaRepository<Category, Integer>{
}
