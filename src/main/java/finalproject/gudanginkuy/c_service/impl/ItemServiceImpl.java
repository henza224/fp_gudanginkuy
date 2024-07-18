package finalproject.gudanginkuy.c_service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import finalproject.gudanginkuy.a_model.Category;
import finalproject.gudanginkuy.a_model.Item;
import finalproject.gudanginkuy.a_model.Vendor;
import finalproject.gudanginkuy.b_repository.ItemRepository;
import finalproject.gudanginkuy.c_service.CategoryService;
import finalproject.gudanginkuy.c_service.ItemService;
import finalproject.gudanginkuy.c_service.VendorService;
import finalproject.gudanginkuy.utils.BarcodeGenerator;
import finalproject.gudanginkuy.utils.dto.ItemDTO;
import finalproject.gudanginkuy.utils.specification.ItemSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final VendorService vendorService;
    private final CategoryService categoryService;
    private final Cloudinary cloudinary;

    @Override
    public Page<Item> getAll(String name, Integer quantity, Pageable pageable) {
        Specification<Item> specification = ItemSpecification.getSpecification(name, quantity);
        return itemRepository.findAll(specification, pageable);
    }

    @Override
    public Item getOne(Integer id) {
        return itemRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FOUND));
    }

    @Override
    public Item create(ItemDTO request) {
        Vendor vendor = vendorService.getOne(request.getVendor_id());
        Category category = categoryService.getOne(request.getCategory_id());

        Item creating = new Item();
        creating.setBarcode(request.getBarcode());
        creating.setName(request.getName());
        creating.setQuantity(request.getQuantity());
        creating.setVendor(vendor);
        creating.setCategory(category);
        return itemRepository.save(creating);
    }

    @Override
    public Item update(Integer id, ItemDTO request) {
        Item itemToUpdate = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (request.getVendor_id() != null) {
            Vendor vendor = vendorService.getOne(request.getVendor_id());
            itemToUpdate.setVendor(vendor);
        }

        if (request.getCategory_id() != null) {
            Category category = categoryService.getOne(request.getCategory_id());
            itemToUpdate.setCategory(category);
        }

        if (request.getBarcode() != null) {
            itemToUpdate.setBarcode(request.getBarcode());
        }

        if (request.getName() != null) {
            itemToUpdate.setName(request.getName());
        }

        if (request.getQuantity() != null) {
            itemToUpdate.setQuantity(request.getQuantity());
        }

        return itemRepository.save(itemToUpdate);
    }

    @Override
    public void delete(Integer id) {
        itemRepository.deleteById(id);
    }

    @Override
    public byte[] generateBarcode(Integer id) throws Exception {
        Item item = getOne(id);
        String barcodeText = item.getBarcode().toString();
        return BarcodeGenerator.generateBarcode(barcodeText);
    }

    @Override
    public Item updateItemPictureUrl(Integer id, String pictureUrl) {
        Item item = this.getOne(id);
        item.setPictureUrl(pictureUrl);
        return itemRepository.save(item);
    }

    public String uploadPicture(MultipartFile picture) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(picture.getBytes(), ObjectUtils.emptyMap());
        return (String) uploadResult.get("url");
    }

    public Item deleteOldPicture(Integer id, MultipartFile picture) throws IOException{
        Item item = this.getOne(id);
        if (item.getPictureUrl() != null) {
            String publicId = extractPublicIdFromUrl(item.getPictureUrl());
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        }

        Map uploadResult = cloudinary.uploader().upload(picture.getBytes(), ObjectUtils.emptyMap());
        String newPictureUrl = (String) uploadResult.get("url");
        item.setPictureUrl(newPictureUrl);
        return itemRepository.save(item);
    }

    private String extractPublicIdFromUrl(String imageUrl) {
        int startIndex = imageUrl.indexOf("/upload/") + "/upload/".length();
        int endIndex = imageUrl.lastIndexOf(".");
        return imageUrl.substring(startIndex, endIndex);
    }
}