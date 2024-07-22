package finalproject.gudanginkuy.d_controller;


import com.google.zxing.NotFoundException;
import finalproject.gudanginkuy.a_model.Transaction;
import finalproject.gudanginkuy.a_model.TransactionType;
import finalproject.gudanginkuy.c_service.TransactionService;
import finalproject.gudanginkuy.utils.dto.TransactionDTO;
import finalproject.gudanginkuy.utils.dto.TransactionSummaryDTO;
import finalproject.gudanginkuy.utils.response.PageWrapper;
import finalproject.gudanginkuy.utils.response.Res;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
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
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        return Res.renderJson(
                transactionService.getOne(id),
                "FOUND",
                HttpStatus.FOUND
        );
    }
    @GetMapping()
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> getAll(
            @RequestParam (required = false) TransactionType type,
            @RequestParam (required = false) LocalDate date,
            @RequestParam (required = false) String itemName,
            @PageableDefault Pageable pageable
    ){
        Page<Transaction> res = transactionService.getAll(type, date, itemName, pageable);
        PageWrapper<Transaction> result = new PageWrapper<>(res);
        return Res.renderJson(
                result,
                "FOUND",
                HttpStatus.FOUND
        );
    }

    @PostMapping("/by-barcode")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
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

    @GetMapping("/summaries")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Page<TransactionSummaryDTO>> getTransactionSummariesByItemName(
            @RequestParam String itemName,
            Pageable pageable) {
        Page<TransactionSummaryDTO> summaries = transactionService.getTransactionSummariesByItemName(itemName, pageable);
        return ResponseEntity.ok(summaries);
    }
}

