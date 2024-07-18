package finalproject.gudanginkuy.d_controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalproject.gudanginkuy.a_model.Category;
import finalproject.gudanginkuy.b_repository.CategoryRepository;
import finalproject.gudanginkuy.utils.response.WebResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class categoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateCategory() throws Exception {
        Category category = new Category();
        category.setName("Makanan");
        categoryRepository.save(category);

        mockMvc.perform(
                post("/category")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category))
        ).andExpectAll(
                status().isCreated()
        ).andDo(result -> {
            WebResponse<Category> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});

            assertEquals("Created", response.getStatus());
            assertEquals("Created", response.getMessage());
            assertNotNull(response.getData().getId());
            assertEquals("Makanan", response.getData().getName());
        });
    }

    @Test
    void testGetCategory() throws Exception {
        Category category = new Category();
        category.setName("Makanan");
        categoryRepository.save(category);

        mockMvc.perform(
                get("/category")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isFound()
        ).andDo(result -> {
            WebResponse<Category> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});

            assertEquals("Found", response.getStatus());
            assertEquals("FOUND", response.getMessage());
        });
    }

    @Test
    void testGetCategoryById() throws Exception {
        Category category = new Category();
        category.setName("Makanan");
        categoryRepository.save(category);

        mockMvc.perform(
                get("/category/1")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isFound()
        ).andDo(result -> {
            WebResponse<Category> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});

            assertEquals("Found", response.getStatus());
            assertEquals("FOUND", response.getMessage());
            assertNotNull(response.getData().getId());
            assertEquals("Makanan", response.getData().getName());
        });
    }
}