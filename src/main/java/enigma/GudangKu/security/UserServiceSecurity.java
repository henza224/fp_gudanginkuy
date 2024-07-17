package enigma.GudangKu.security;

import enigma.GudangKu.model.User;
import enigma.GudangKu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceSecurity implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    return new org.springframework.security.core.userdetails.User(
            user.getUsername(),user.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString()))
    );
    }
}
