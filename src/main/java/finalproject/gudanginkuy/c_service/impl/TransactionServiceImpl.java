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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ItemService itemService;

    @Override
    public Page<Transaction> getAll(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }

    @Override
    public Transaction getOne(Integer id) {
        return transactionRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    @Override
    public Transaction create(TransactionDTO request, TransactionType type) {
        Item item = itemService.getOne(request.getItemId());

        Transaction creating = new Transaction();
        creating.setItem(item);
        creating.setTimestamp(LocalDateTime.now());
        if (type == TransactionType.IN) {
            if (item.getQuantity() == null){
                creating.setQuantity(request.getQuantity());
            }
            creating.setQuantity(item.getQuantity() + request.getQuantity());
            creating.setType(type);

        } else if (type == TransactionType.OUT) {
            creating.setQuantity(item.getQuantity() - request.getQuantity());
            creating.setType(type);
        }
        return transactionRepository.save(creating);
    }
}
