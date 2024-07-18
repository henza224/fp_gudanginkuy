package finalproject.gudanginkuy.c_service;

import finalproject.gudanginkuy.a_model.Transaction;
import finalproject.gudanginkuy.a_model.TransactionType;
import finalproject.gudanginkuy.utils.dto.TransactionDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {
    Page<Transaction> getAll (Pageable pageable);
    Transaction getOne (Integer id);
    Transaction create (TransactionDTO request, TransactionType type, HttpServletRequest token);
}
