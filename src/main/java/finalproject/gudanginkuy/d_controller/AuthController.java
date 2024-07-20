package finalproject.gudanginkuy.d_controller;

import finalproject.gudanginkuy.a_model.Role;
import finalproject.gudanginkuy.a_model.User;
import finalproject.gudanginkuy.b_repository.UserRepository;
import finalproject.gudanginkuy.utils.dto.UserDTO;
import finalproject.gudanginkuy.utils.response.Res;
import finalproject.gudanginkuy.utils.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;


    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return Res.renderJson(null, "username is taken", HttpStatus.BAD_REQUEST);
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        if (newUser.getRole() == null) {
            newUser.setRole(Role.ROLE_USER);
        }
        return Res.renderJson(
                userRepository.save(newUser),
                "User Successfully Registered!",
                HttpStatus.CREATED
        );
    }

    @PostMapping("/register/admin")
    @Transactional
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody UserDTO request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return Res.renderJson(null, "username is taken", HttpStatus.BAD_REQUEST);
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        if (newUser.getRole() == null) {
            newUser.setRole(Role.ROLE_ADMIN);
        }
        return Res.renderJson(
                userRepository.save(newUser),
                "Admin Successfully Registered!",
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDTO request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = tokenProvider.generateToken(auth);
        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);

        return Res.renderJson(
                response,
                "Login Successful!",
                HttpStatus.OK
        );
//        if (request.getUsername() == null || request.getPassword() == null) {
//           return Res.renderJson(
//                    request, "Username or password cannot be null", HttpStatus.BAD_REQUEST
//            );
//        }
//
//        // Autentikasi
//        Authentication auth = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getUsername(),
//                        request.getPassword()
//                )
//        );
//
//
//        if (auth.isAuthenticated()) {
//            SecurityContextHolder.getContext().setAuthentication(auth);
//
//            String jwt = tokenProvider.generateToken(auth);
//            Map<String, String> response = new HashMap<>();
//            response.put("token", jwt);
//
//            return Res.renderJson(
//                    response,
//                    "Login Successful!",
//                    HttpStatus.OK
//            );
//        } else {
//            return Res.renderJson(
//                    request, "Invalid username or password", HttpStatus.UNAUTHORIZED
//            );
    }


    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
            SecurityContextHolder.clearContext();
            Map<String, Object> response = new HashMap<>();
            response.put("User", username);

            return Res.renderJson(
                    response,
                    "Logout Successful!",
                    HttpStatus.OK);

    }
}


//    @PostConstruct
//    public void initAdmin() {
//        String username = "admin";
//        String password = "admin";
//
//        Optional<User> optionalUserCredential = userRepository.findByUsername(username);
//        if(optionalUserCredential.isPresent()) {
//            return;
//        }
//
//        Role roleAdmin = Role.ROLE_ADMIN;
//        User userCredential = User.builder()
//                .username(username)
//                .password(passwordEncoder.encode(password))
//                .role(roleAdmin)
//                .build();
//        userRepository.save(userCredential);
//    }
//}
