package finalproject.gudanginkuy.d_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import finalproject.gudanginkuy.a_model.User;
import finalproject.gudanginkuy.b_repository.UserRepository;
import finalproject.gudanginkuy.utils.dto.UserDTO;
import finalproject.gudanginkuy.utils.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")

class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JwtTokenProvider tokenProvider;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setPassword("password");

        reset(userRepository, authenticationManager, passwordEncoder, tokenProvider);
    }

    @Test
    void testRegisterSuccess() throws Exception {
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setUsername("newuser");
        newUserDTO.setPassword("password");

        when(userRepository.findByUsername(newUserDTO.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(
                        post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newUserDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is("User Successfully Registered!")));

        verify(userRepository).findByUsername(newUserDTO.getUsername());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testRegisterUsernameTaken() throws Exception {
        User existingUser = new User();
        existingUser.setUsername(userDTO.getUsername());

        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.of(existingUser));

        mockMvc.perform(
                        post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("username is taken")));

        verify(userRepository).findByUsername(userDTO.getUsername());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testLoginSuccess() throws Exception {
        Authentication auth = new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(tokenProvider.generateToken(auth)).thenReturn("mock-jwt-token");

        mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Login Successful!")))
                .andExpect(jsonPath("$.data.token", is("mock-jwt-token")));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenProvider).generateToken(auth);
    }

    @Test
    void testLogoutSuccess() throws Exception {
        Authentication auth = new UsernamePasswordAuthenticationToken("testuser", null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        mockMvc.perform(delete("/auth/logout"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Logout Successful!")));

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}

