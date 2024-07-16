package finalproject.gudanginkuy.c_service;

import finalproject.gudanginkuy.a_model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StockService {
    Page<User> getAll (String nama_item, Pageable pageable);
    User getOne (Integer id);
    User create (User request);
    User update (Integer id, User request);
    void delete (Integer id);
}
