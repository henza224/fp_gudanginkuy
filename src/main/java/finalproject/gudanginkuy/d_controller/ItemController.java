package finalproject.gudanginkuy.d_controller;

import finalproject.gudanginkuy.a_model.Item;
import finalproject.gudanginkuy.a_model.User;
import finalproject.gudanginkuy.c_service.ItemService;
import finalproject.gudanginkuy.utils.dto.ItemDTO;
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
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody ItemDTO request){
        return Res.renderJson(
                itemService.create(request),
                "Created",
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        return Res.renderJson(
                itemService.getOne(id),
                "FOUND",
                HttpStatus.FOUND
        );
    }
    @GetMapping()
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer quantity,
            @PageableDefault Pageable pageable
    ){
        Page<Item> res = itemService.getAll(name, quantity, pageable);
        PageWrapper<Item> result = new PageWrapper<>(res);
        return Res.renderJson(
                result,
                "FOUND",
                HttpStatus.FOUND
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<?>  update(@PathVariable Integer id, @RequestBody ItemDTO request){
        return Res.renderJson(
                itemService.update(id, request),
                "Update Sucsess",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Integer id
    ) {
        itemService.delete(id);
        return new ResponseEntity<>("Delete sukses", HttpStatus.OK);
    }


}
