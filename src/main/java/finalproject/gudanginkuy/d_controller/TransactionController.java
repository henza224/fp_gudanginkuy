package finalproject.gudanginkuy.d_controller;


import com.google.zxing.NotFoundException;
import finalproject.gudanginkuy.a_model.Transaction;
import finalproject.gudanginkuy.a_model.TransactionType;
import finalproject.gudanginkuy.c_service.TransactionService;
import finalproject.gudanginkuy.utils.dto.TransactionDTO;
import finalproject.gudanginkuy.utils.response.PageWrapper;
import finalproject.gudanginkuy.utils.response.Res;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/Transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> create(
            HttpServletRequest token,
            @RequestParam TransactionType type,
            @RequestBody TransactionDTO request){
        return Res.renderJson(
                transactionService.create(request, type, token),
                "Created",
                HttpStatus.CREATED
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        return Res.renderJson(
                transactionService.getOne(id),
                "FOUND",
                HttpStatus.FOUND
        );
    }
    @GetMapping()
    public ResponseEntity<?> getAll(
            @PageableDefault Pageable pageable
    ){
        PageWrapper<Transaction> result =
                new PageWrapper<>(transactionService.getAll(pageable));
        return Res.renderJson(
                result,
                "FOUND",
                HttpStatus.FOUND
        );
    }

    @PostMapping("/by-barcode")
    public ResponseEntity<?> createTransactionByBarcodeImage(
            HttpServletRequest token,
            @RequestParam TransactionType type,
            @RequestParam("barcodeImage") MultipartFile barcodeImage) {
        try {
            Transaction transaction = transactionService.createTransactionByBarcodeImage(barcodeImage, type, token);
            return Res.renderJson(
                    transaction,
                    "Created",
                    HttpStatus.CREATED
            );
        } catch (IOException | NotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing barcode image: " + e.getMessage());
        }
    }

}