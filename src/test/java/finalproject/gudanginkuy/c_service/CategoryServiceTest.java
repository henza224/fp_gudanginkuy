package finalproject.gudanginkuy.c_service;

import finalproject.gudanginkuy.a_model.Category;
import finalproject.gudanginkuy.b_repository.CategoryRepository;
import finalproject.gudanginkuy.c_service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;
    
    @InjectMocks
    private CategoryServiceImpl categoryService;
    
    @Test
    public void CategoryService_Create_ReturnCreatedCategory(){
        Category category = mock(Category.class);
        Category category1= Category.builder()
                .categoryName("category")
                .build();

        when(categoryRepository.save(any(Category.class)))
                .thenReturn(category);
        Category savedCategory = categoryService.create(category1);

        assertThat(savedCategory).isNotNull();
    }

    @Test
    public void CategoryService_GetById_ReturnCategory() {
        Category category = mock(Category.class);

        when(categoryRepository.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(category));
        Category foundCustomer = categoryService.getOne(1);

        assertThat(foundCustomer).isNotNull();
    }
    @Test
    public void CategoryService_UpdateById_ReturnUpdatedCategory() {
        Category category1 = mock(Category.class);
        Category category = Category.builder()
                .categoryName("customer 1")
                .build();

        when(categoryRepository.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(category1));
        when(categoryRepository.save(any(Category.class)))
                .thenReturn(category1);
        Category updatedCategory = categoryService.update(1,category);

        assertThat(updatedCategory).isNotNull();
    }

    @Test
    public void CategoryService_DeleteById_Void() {
        Category category = mock(Category.class);

        assertAll(() -> categoryService.delete(1));
    }

    @Test
    public void CategoryService_GetAll_ReturnAllCategory() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> categoryPage = new PageImpl<>(Collections.emptyList());

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);

        // Act
        Page<Category> result = categoryService.getAll(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
    }


}
