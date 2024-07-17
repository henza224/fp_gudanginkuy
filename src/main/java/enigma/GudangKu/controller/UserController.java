package enigma.GudangKu.controller;

import enigma.GudangKu.model.User;
import enigma.GudangKu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public User create(@RequestBody User req){
        return userService.create(req);
    }
    @GetMapping("/{id}")
    public User getOne(@PathVariable Integer id){
        return userService.getOne(id);
    }
    @GetMapping
    public List<User> getAll(@RequestParam (required = false)String username,
                             @RequestParam(required = false)Integer noTelp){
        return userService.getAll(username, noTelp);
    }
    @PutMapping("/{id}")
    public User update (@PathVariable Integer id, @RequestBody User req){
        return userService.update(id, req);
    }
    @DeleteMapping("/{id}")
    public void delete (@PathVariable Integer id){
        userService.delete(id);
    }
}
