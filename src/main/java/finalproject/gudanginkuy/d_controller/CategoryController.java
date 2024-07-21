package finalproject.gudanginkuy.d_controller;

import finalproject.gudanginkuy.a_model.Category;
import finalproject.gudanginkuy.c_service.CategoryService;
import finalproject.gudanginkuy.utils.dto.ItemDTO;
import finalproject.gudanginkuy.utils.response.PageWrapper;
import finalproject.gudanginkuy.utils.response.Res;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> create(@RequestBody Category request){
        return Res.renderJson(
                categoryService.create(request),
                "Created",
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        return Res.renderJson(
                categoryService.getOne(id),
                "FOUND",
                HttpStatus.FOUND
        );
    }
    @GetMapping()
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> getAll(
            @PageableDefault Pageable pageable
    ){
        PageWrapper<Category> result = new PageWrapper<>(categoryService.getAll(pageable));
        return Res.renderJson(
                result,
                "FOUND",
                HttpStatus.FOUND
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?>  update(@PathVariable Integer id, @RequestBody Category request){
        return Res.renderJson(
                categoryService.update(id, request),
                "Update Sucsess",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> delete(
            @PathVariable Integer id
    ) {
        categoryService.delete(id);
        return new ResponseEntity<>("Delete sukses", HttpStatus.OK);
    }
}
