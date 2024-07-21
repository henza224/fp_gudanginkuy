package finalproject.gudanginkuy.d_controller;

import finalproject.gudanginkuy.a_model.Vendor;
import finalproject.gudanginkuy.c_service.VendorService;
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
@RequestMapping("/vendor")
@RequiredArgsConstructor
public class VendorController {
    private final VendorService vendorService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> create(@RequestBody Vendor request){
        return Res.renderJson(
                vendorService.create(request),
                "Created",
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        return Res.renderJson(
                vendorService.getOne(id),
                "FOUND",
                HttpStatus.FOUND
        );
    }
    @GetMapping()
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
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
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> delete(
            @PathVariable Integer id
    ) {
        vendorService.delete(id);
        return new ResponseEntity<>("Delete sukses", HttpStatus.OK);
    }
}
