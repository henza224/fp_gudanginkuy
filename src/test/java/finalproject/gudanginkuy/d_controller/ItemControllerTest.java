package finalproject.gudanginkuy.d_controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalproject.gudanginkuy.a_model.Category;
import finalproject.gudanginkuy.a_model.Item;
import finalproject.gudanginkuy.a_model.Vendor;
import finalproject.gudanginkuy.b_repository.ItemRepository;
import finalproject.gudanginkuy.c_service.CategoryService;
import finalproject.gudanginkuy.c_service.ItemService;
import finalproject.gudanginkuy.c_service.VendorService;
import finalproject.gudanginkuy.utils.dto.ItemDTO;
import finalproject.gudanginkuy.utils.response.WebResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateItem() throws Exception {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setBarcode(String.valueOf(1));
        itemDTO.setName("Le Mineral");
        itemDTO.setQuantity(100);
        itemDTO.setVendor_id(1);
        itemDTO.setCategory_id(1);
        itemService.create(itemDTO);

        mockMvc.perform(
                post("/item")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString (itemDTO))
        ).andExpectAll(
                status().isCreated()
        ).andDo(result -> {
            WebResponse<Item> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertEquals("Created", response.getStatus());
            assertEquals("Created", response.getMessage());
            assertEquals(1, response.getData().getBarcode());
            assertEquals("Le Mineral", response.getData().getName());
            assertEquals(100, response.getData().getQuantity());
            assertEquals(1, response.getData().getVendor().getId());
            assertEquals(1, response.getData().getCategory().getId());
        });
    }

    @Test
    void testGetAllItem() throws Exception {
        mockMvc.perform(
                get("/item")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isFound()
        ).andDo(result -> {
            WebResponse<Item> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertEquals("Found", response.getStatus());
            assertEquals("FOUND", response.getMessage());
            assertNotNull(response.getData());
        });
    }

    @Test
    void testGetItemById() throws Exception {
        mockMvc.perform(
                get("/item/1")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isFound()
        ).andDo(result -> {
            WebResponse<Item> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertEquals("Found", response.getStatus());
            assertEquals("FOUND", response.getMessage());
            assertEquals(1, response.getData().getId());
            assertEquals(1, response.getData().getBarcode());
            assertEquals("Le Mineral", response.getData().getName());
            assertEquals(100, response.getData().getQuantity());
            assertEquals(1, response.getData().getVendor().getId());
            assertEquals(1, response.getData().getCategory().getId());
        });
    }

    @Test
    void testUpdateItem() throws Exception {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setBarcode(String.valueOf(1));
        itemDTO.setName("Le Mineral");
        itemDTO.setQuantity(300);
        itemDTO.setVendor_id(1);
        itemDTO.setCategory_id(1);
        itemService.update(1, itemDTO);

        mockMvc.perform(
                put("/item/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString (itemDTO))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<Item> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertEquals("OK", response.getStatus());
            assertEquals("Update Sucsess", response.getMessage());
            assertEquals(1, response.getData().getId());
            assertEquals(1, response.getData().getBarcode());
            assertEquals("Le Mineral", response.getData().getName());
            assertEquals(300, response.getData().getQuantity());
            assertEquals(1, response.getData().getVendor().getId());
            assertEquals(1, response.getData().getCategory().getId());
        });
    }
}