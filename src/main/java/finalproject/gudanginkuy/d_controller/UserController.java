package finalproject.gudanginkuy.d_controller;

import finalproject.gudanginkuy.a_model.User;
import finalproject.gudanginkuy.c_service.UserService;
import finalproject.gudanginkuy.utils.response.PageWrapper;
import finalproject.gudanginkuy.utils.response.Res;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

//    @PostMapping
//    public ResponseEntity<?> create(@RequestBody User request){
//        return Res.renderJson(
//                userService.create(request),
//                "Created",
//                HttpStatus.CREATED
//        );
//    }


    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) String username,
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        Page<User> res = userService.getAll(username, pageable);
        PageWrapper<User> result = new PageWrapper<>(res);
        return Res.renderJson(
                result,
                "FOUND",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        return Res.renderJson(
                userService.getOne(id),
                "FOUND",
                HttpStatus.OK
        );
    }


    @PutMapping("/gantiusername/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> updategantiusername(@PathVariable Integer id, @RequestBody User users){
        return Res.renderJson(
                userService.updategantiusername(id, users),
                "Success Update Username",
                HttpStatus.OK
        );
    }

    @PutMapping("/gantipassword/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> updategantipassword(@PathVariable Integer id, @RequestBody User users){
        return Res.renderJson(
                userService.updategantipassword(id, users),
                "Success Update Password",
                HttpStatus.OK
        );
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void delete(@PathVariable Integer id){
        userService.delete(id);
    }
}
