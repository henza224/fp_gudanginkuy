package finalproject.gudanginkuy.d_controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalproject.gudanginkuy.a_model.Role;
import finalproject.gudanginkuy.a_model.User;
import finalproject.gudanginkuy.b_repository.UserRepository;
import finalproject.gudanginkuy.c_service.UserService;
import finalproject.gudanginkuy.utils.response.WebResponse;
import finalproject.gudanginkuy.utils.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

     @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateUser() throws Exception {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        userService.create(user);

        mockMvc.perform(
                post("/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        ).andExpectAll(
                status().isCreated()
        ).andDo(result -> {
            WebResponse<User> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});

            assertEquals("Created", response.getStatus());
            assertEquals("Created", response.getMessage());
            assertNotNull(response.getData().getId());
            assertEquals("username", response.getData().getUsername());
            assertEquals("password", response.getData().getPassword());
        });
    }

    @Test
    void testGetUserById() throws Exception {
        mockMvc.perform(
                get("/users/8")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<User> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertEquals("OK", response.getStatus());
            assertEquals("FOUND", response.getMessage());
            assertEquals(8, response.getData().getId());
            assertEquals("username", response.getData().getUsername());
            assertEquals("password", response.getData().getPassword());
        });
    }

    @Test
    void testGantiUsername() throws Exception {
        User user = userService.getOne(8);
        user.setUsername("Budi");
        userService.updategantipassword(8, user);

        mockMvc.perform(
                put("/users/gantiusername/8")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<User> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertEquals("OK", response.getStatus());
            assertEquals("Success Update Username", response.getMessage());
            assertEquals(8, response.getData().getId());
            assertEquals("Budi", response.getData().getUsername());
        });
    }

    @Test
    void testGantiPassword() throws Exception {
        User user = userService.getOne(8);
        user.setPassword("Budi123");
        userService.updategantipassword(8, user);

        mockMvc.perform(
                put("/users/gantipassword/8")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<User> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertEquals("OK", response.getStatus());
            assertEquals("Success Update Password", response.getMessage());
            assertEquals(8, response.getData().getId());
            assertEquals("Budi123", response.getData().getPassword());
        });
    }
}