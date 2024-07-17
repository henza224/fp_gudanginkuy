package finalproject.gudanginkuy.d_controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalproject.gudanginkuy.a_model.Category;
import finalproject.gudanginkuy.a_model.Item;
import finalproject.gudanginkuy.a_model.Vendor;
import finalproject.gudanginkuy.b_repository.CategoryRepository;
import finalproject.gudanginkuy.b_repository.ItemRepository;
import finalproject.gudanginkuy.b_repository.VendorRepository;
import finalproject.gudanginkuy.c_service.CategoryService;
import finalproject.gudanginkuy.c_service.VendorService;
import finalproject.gudanginkuy.utils.response.WebResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateItem() throws Exception {
        Vendor vendor = new Vendor();
        vendor.setVendorName("Bakti Karya");
        vendor.setAddress("Jl. Abdul Wahab RT.03 RW.03 Sawangan, Depok");
        vendor.setNoTelephone(123456789);

        vendorService.create(vendor);
        Category category = new Category();
        category.setName("Makanan");
        categoryService.create(category);

        Item item = new Item();
        item.setBarcode(12);
        item.setName("test");
        item.setQuantity(100);
        item.setVendor(vendor);
        item.setCategory(category);
        itemRepository.save(item);

        mockMvc.perform(
                post("/item")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString (item))
        ).andExpectAll(
                status().isCreated()
        ).andDo(result -> {
            WebResponse<Item> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertEquals("Created", response.getStatus());
            assertEquals("Created", response.getMessage());
        });
    }
}