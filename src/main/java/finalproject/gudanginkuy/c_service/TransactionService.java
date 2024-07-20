package finalproject.gudanginkuy.c_service;

import com.google.zxing.NotFoundException;
import finalproject.gudanginkuy.a_model.Transaction;
import finalproject.gudanginkuy.a_model.TransactionType;
import finalproject.gudanginkuy.utils.dto.TransactionDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

public interface TransactionService {
    Page<Transaction> getAll (TransactionType type, LocalDate date, String itemName, Pageable pageable);
    Transaction getOne (Integer id);
    Transaction create (TransactionDTO request, TransactionType type, HttpServletRequest token);

    Transaction createTransactionByBarcodeImage(
            MultipartFile barcodeImage, TransactionType type, HttpServletRequest token)
            throws IOException, NotFoundException;
}