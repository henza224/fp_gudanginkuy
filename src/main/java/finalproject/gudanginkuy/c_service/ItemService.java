package finalproject.gudanginkuy.c_service;

import com.google.zxing.NotFoundException;
import finalproject.gudanginkuy.a_model.Item;
import finalproject.gudanginkuy.utils.dto.ItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.io.InputStream;
public interface ItemService {
    Page<Item> getAll(String name, String category, String vendor, Integer quantity, Pageable pageable);
    Item getOne(Integer id);
    Item create(ItemDTO Request);
    Item update(Integer id, ItemDTO request);
    void delete(Integer id);
    Item updateItemPictureUrl(Integer id, String pictureUrl);
    byte[] generateBarcode(Integer id) throws Exception;

    Item getByBarcode(String barcode);
    Item getByBarcodeImage(InputStream barcodeImage) throws IOException, NotFoundException;
}