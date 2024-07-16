package finalproject.gudanginkuy.c_service;

import finalproject.gudanginkuy.a_model.Transaction;
import finalproject.gudanginkuy.a_model.User;
import finalproject.gudanginkuy.utils.dto.TransactionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {
    Page<Transaction> getAll (String namaItem, Pageable pageable);
    Transaction getOne (Integer id);
    Transaction create (TransactionDTO request);
    void delete (Integer id);
}
