package finalproject.gudanginkuy.c_service.impl;

import finalproject.gudanginkuy.a_model.Item;
import finalproject.gudanginkuy.a_model.Transaction;
import finalproject.gudanginkuy.a_model.TransactionType;
import finalproject.gudanginkuy.b_repository.TransactionRepository;
import finalproject.gudanginkuy.c_service.ItemService;
import finalproject.gudanginkuy.c_service.TransactionService;
import finalproject.gudanginkuy.utils.dto.TransactionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ItemServiceImpl itemService;
    private final UserServiceImpl userService;


    @Override
    public Page<Transaction> getAll(String namaItem, Pageable pageable) {
        return null;
    }

    @Override
    public Transaction getOne(Integer id) {
        return transactionRepository.findById(id);
    }

    @Override
    public Transaction create(TransactionDTO request, TransactionType tes) {
        User userU = userService.getOne(request.getUser_id());
        Item itemU = itemService.getOne(request.getItem_id());

        Transaction creating  = new Transaction();
        creating.setUser(userU);
        creating.setItem(itemU);
        creating.setDate(LocalDate.now());

        if (tes == TransactionType.TRANSAKSI_IN){
            creating.setQuantity(itemU.getQuantity() + request.getQuantity());
            creating.setType(tes);
        } else if (tes == TransactionType.TRANSAKSI_OUT) {
            creating.setQuantity(itemU.getQuantity() - request.getQuantity());
            if (creating.getQuantity() == 0){
                return null;
            }
            creating.setType(tes);
        }
        return creating;
    }

    @Override
    public void delete(Integer id) {
        transactionRepository.deleteById(id);
    }
}
