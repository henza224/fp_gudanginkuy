package finalproject.gudanginkuy.c_service;

import finalproject.gudanginkuy.a_model.Item;
import finalproject.gudanginkuy.utils.dto.ItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {
    Page<Item> getAll(String name, Integer quantity, Pageable pageable);
    Item getOne(Integer id);
    Item create(ItemDTO Request);
    Item updateQuantity(Integer id, Integer quantity);
    void delete(Integer id);
    byte[] generateBarcode(Integer id) throws Exception;
}