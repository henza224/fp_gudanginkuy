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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
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
    public User getOne(@PathVariable Integer id) {
        return userService.getOne(id);
    }


    @PutMapping("/gantiusername/{id}")
    public User updategantiusername(@PathVariable Integer id, @RequestBody User users){
        return userService.updategantiusername(id, users);
    }

    @PutMapping("/gantipassword{id}")
    public User updategantipassword(@PathVariable Integer id, @RequestBody User users){
        return userService.updategantipassword(id, users);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        userService.delete(id);
    }
}
