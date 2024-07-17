package finalproject.gudanginkuy.c_service.impl;

import finalproject.gudanginkuy.a_model.Category;
import finalproject.gudanginkuy.b_repository.CategoryRepository;
import finalproject.gudanginkuy.c_service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Page<Category> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Category getOne(Integer id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Category create(Category request) {
        return categoryRepository.save(request);
    }

    @Override
    public void delete(Integer id) {
        categoryRepository.deleteById(id);

    }
}
