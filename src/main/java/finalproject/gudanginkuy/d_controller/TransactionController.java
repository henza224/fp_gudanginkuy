package finalproject.gudanginkuy.d_controller;


import finalproject.gudanginkuy.a_model.Transaction;
import finalproject.gudanginkuy.a_model.TransactionType;
import finalproject.gudanginkuy.c_service.TransactionService;
import finalproject.gudanginkuy.utils.dto.TransactionDTO;
import finalproject.gudanginkuy.utils.response.PageWrapper;
import finalproject.gudanginkuy.utils.response.Res;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/transaction")
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

}
