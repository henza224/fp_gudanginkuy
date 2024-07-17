package finalproject.gudanginkuy.c_service;

import finalproject.gudanginkuy.a_model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<Category> getAll(Pageable pageable);
    Category getOne(Integer id);
    Category create(Category request);
    Category update(Integer id, Category request);
    void delete (Integer id);
}
