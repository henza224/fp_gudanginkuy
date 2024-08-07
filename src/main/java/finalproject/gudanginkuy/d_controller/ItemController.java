package finalproject.gudanginkuy.d_controller;


import com.google.zxing.NotFoundException;
import finalproject.gudanginkuy.a_model.Item;
import finalproject.gudanginkuy.c_service.ItemService;
import finalproject.gudanginkuy.c_service.impl.ItemServiceImpl;
import finalproject.gudanginkuy.utils.dto.ItemDTO;
import finalproject.gudanginkuy.utils.response.PageWrapper;
import finalproject.gudanginkuy.utils.response.Res;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final ItemServiceImpl itemServiceImpl;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> create(
            @RequestBody ItemDTO request){
        return Res.renderJson(
                itemService.create(request),
                "Created",
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        return Res.renderJson(
                itemService.getOne(id),
                "FOUND",
                HttpStatus.FOUND
        );
    }
    @GetMapping()
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String vendor,
            @RequestParam(required = false) Integer quantity,
            @PageableDefault Pageable pageable
    ){
        Page<Item> res = itemService.getAll(name, category, vendor, quantity, pageable);
        PageWrapper<Item> result = new PageWrapper<>(res);
        return Res.renderJson(
                result,
                "FOUND",
                HttpStatus.FOUND
        );
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?>  update(@PathVariable Integer id, @RequestBody ItemDTO request){
        return Res.renderJson(
                itemService.update(id, request),
                "Update Sucsess",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> delete(
            @PathVariable Integer id
    ) {
        itemService.delete(id);
        return new ResponseEntity<>("Delete sukses", HttpStatus.OK);
    }

    @GetMapping("/{id}/barcode")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> generateBarcode(@PathVariable Integer id) {
        try {
            byte[] barcodeImage = itemService.generateBarcode(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE)
                    .body(barcodeImage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating barcode: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/upload")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> uploadPicture (@PathVariable Integer id, @RequestParam("picture") MultipartFile file){
        try {
            String pictureUrl = itemServiceImpl.uploadPicture(file);
            itemService.updateItemPictureUrl(id, pictureUrl);
            return Res.renderJson(
                    pictureUrl,
                    "UPLOAD SUCCESS",
                    HttpStatus.OK
            );
        } catch (IOException e){
            return ResponseEntity.status(500).body("Error uploading picture");
        }
    }

    @PutMapping("/{id}/newpicture")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> newPicture(@PathVariable Integer id, @RequestParam("picture") MultipartFile file){
        try {
            Item updatedPicture = itemServiceImpl.deleteOldPicture(id, file);
            updatedPicture.getPictureUrl();
            return Res.renderJson(
                    updatedPicture,"PICTURE UPDATED SUCCESS",
                    HttpStatus.OK
            );

        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error upload new picture");
        }
    }

    @PostMapping("/search-by-barcode")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> searchByBarcodeImage(@RequestParam("barcodeImage") MultipartFile barcodeImage) {
        try {
            Item item = itemServiceImpl.getByBarcodeImage(barcodeImage.getInputStream());
            return Res.renderJson(
                    item,
                    "FOUND",
                    HttpStatus.FOUND
            );
        } catch (IOException | NotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing barcode image: " + e.getMessage());
        }
    }
}
