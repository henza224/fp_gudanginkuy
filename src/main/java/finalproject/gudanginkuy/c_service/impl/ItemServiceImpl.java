package finalproject.gudanginkuy.c_service.impl;

import finalproject.gudanginkuy.a_model.Item;
import finalproject.gudanginkuy.b_repository.CategoryRepository;
import finalproject.gudanginkuy.b_repository.ItemRepository;
import finalproject.gudanginkuy.b_repository.VendorRepository;
import finalproject.gudanginkuy.c_service.CategoryService;
import finalproject.gudanginkuy.c_service.ItemService;
import finalproject.gudanginkuy.c_service.VendorService;
import finalproject.gudanginkuy.utils.dto.ItemDTO;
import finalproject.gudanginkuy.utils.specification.ItemSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final VendorService vendorService;
    private final CategoryService categoryService;

    @Override
    public Page<Item> getAll(String name, Integer quantity, Pageable pageable) {
        Specification<Item> specification = ItemSpecification.getSpecification(name, quantity);
        return itemRepository.findAll(specification, pageable);
    }
    @Override
    public Item getOne(Integer id) {
        return itemRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @Override
    public Item create(ItemDTO request) {
        Item creating = new Item();
        creating.setBarcode(request.getBarcode());
        creating.setName(request.getName());
        creating.setQuantity(request.getQuantity());
        creating.setVendor(vendorService.getOne(request.getVendor_id()));
        creating.setCategory(categoryService.getOne(request.getCategory_id()));
        return itemRepository.save(creating);
    }
    @Override
    public void delete(Integer id) {
        itemRepository.deleteById(id);
    }
}
