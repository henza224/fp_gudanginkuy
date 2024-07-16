package finalproject.gudanginkuy.d_controller;

import finalproject.gudanginkuy.a_model.Vendor;
import finalproject.gudanginkuy.c_service.VendorService;
import finalproject.gudanginkuy.utils.response.PageWrapper;
import finalproject.gudanginkuy.utils.response.Res;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vendors")
@RequiredArgsConstructor
public class VendorController {
    private final VendorService vendorService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Vendor request){
        return Res.renderJson(
                vendorService.create(request),
                "Created",
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        return Res.renderJson(
                vendorService.getOne(id),
                "FOUND",
                HttpStatus.FOUND
        );
    }
    @GetMapping()
    public ResponseEntity<?> getAll(
            @PageableDefault Pageable pageable
    ){
        PageWrapper<Vendor> result = new PageWrapper<>(vendorService.getAll(pageable));
        return Res.renderJson(
                result,
                "FOUND",
                HttpStatus.FOUND
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Integer id,
            @RequestBody Vendor request
    ) {
        return Res.renderJson(
                vendorService.update(id, request),
                "Updated",
                HttpStatus.OK
        );
    }
    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Integer id
    ) {
        vendorService.delete(id);
    }
}
