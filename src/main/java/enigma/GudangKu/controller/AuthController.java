package enigma.GudangKu.controller;

import enigma.GudangKu.model.Role;
import enigma.GudangKu.model.User;
import enigma.GudangKu.repository.UserRepository;
import enigma.GudangKu.security.JwtTokenProvider;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User req){
        if (userRepository.findByUsername(req.getUsername()).isPresent()){
            return null;
        }
        User newUser = new User();
        newUser.setUsername(req.getUsername());
        newUser.setPassword(req.getPassword());

        if (newUser.getRole() == null){
            newUser.setRole(Role.ROLE_USER);
        }
        return  null;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@Valid @RequestBody User req){
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(),req.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt =tokenProvider.generateToken(auth);
        Map<String,String> response = new HashMap<>();
        response.put("token",jwt);

        return null;
    }

    @PostConstruct
    public void Admin(){
        String username = "admin";
        String password = "admin";

        Optional<User> optionalUserCredential = userRepository.findByUsername(username);
        if(optionalUserCredential.isPresent()) {
            return;
        }
        Role roleAdmin = Role.ROLE_ADMIN;
        User userCredential = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(roleAdmin)
                .build();
        userRepository.save(userCredential);
    }

}
