package finalproject.gudanginkuy.c_service;

import finalproject.gudanginkuy.a_model.Category;
import finalproject.gudanginkuy.a_model.Item;
import finalproject.gudanginkuy.a_model.Vendor;
import finalproject.gudanginkuy.b_repository.ItemRepository;
import finalproject.gudanginkuy.c_service.impl.ItemServiceImpl;
import finalproject.gudanginkuy.utils.dto.ItemDTO;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
    @Mock
    private ItemRepository repository;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private VendorService vendorService;
    @Mock
    private CategoryService categoryService;


    private ItemDTO itemDTO;
    private Item item;
    private Vendor vendor;
    private Category category;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        vendor = Vendor.builder()
                .id(1)
                .vendorName("budi")
                .address("jakarta")
                .noTelephone("087879877777")
                .build();
        category = Category.builder()
                .id(1)
                .categoryName("makanan")
                .build();
        itemDTO = ItemDTO.builder()
                .category_id(1)
                .vendor_id(1)
                .name("test")
                .quantity(10)
                .build();
        item = Item.builder()
                .id(1)
                .quantity(10)
                .name("test")
                .category(category)
                .vendor(vendor)
                .pictureUrl("htttp;//")
                .build();
        itemService.create(itemDTO);
        vendorService.create(vendor);
        categoryService.create(category);

    }

    @Test
    void ItemService_create(){
        when(repository.save(any(Item.class))).thenReturn(item);
        when(vendorService.getOne(anyInt())).thenReturn(vendor);
        when(categoryService.getOne(anyInt())).thenReturn(category);


        Item result = itemService.create(itemDTO);

        assertThat(result).isNotNull();
        verify(vendorService).getOne(itemDTO.getVendor_id());
        verify(categoryService).getOne(itemDTO.getCategory_id());
        verify(repository).save(any(Item.class));

    }

    @Test
    void getAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Item> items = new PageImpl<>(Collections.emptyList());
        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(items);

        Page<Item> result = itemService.getAll("testName", "testCategory", "testVendor", 10, pageable);

        assertNotNull(result);
        assertEquals(items, result);
    }

    @Test
    public void getONe(){
        Item addItem = mock(Item.class);

        when(repository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(addItem));

        Item foundItem = itemService.getOne(1);

        assertThat(foundItem).isNotNull();
    }
    @Test
    public void deleteItem(){
        Item addItem = mock(Item.class);

        assertAll(()-> itemService.delete(1));
    }
    @Test
    public void UpdateItem(){
        Item item1 = mock(Item.class);

        when(repository.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(item1));
        when(repository.save(any(Item.class))).thenReturn(item1)
                .thenReturn(item1);
        Item updateItem = itemService.update(1,itemDTO);

        assertThat(updateItem).isNotNull();
    }
}
