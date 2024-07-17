package enigma.GudangKu.service.impl;

import enigma.GudangKu.model.User;
import enigma.GudangKu.repository.UserRepository;
import enigma.GudangKu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User create(User req) {
        return userRepository.save(req);
    }

    @Override
    public List<User> getAll(String username, Integer noTelp) {
        return userRepository.findAll();
    }

    @Override
    public User getOne(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
    }

    @Override
    public User update(Integer id, User req) {
        User updateUser= this.getOne(id);
        updateUser.setUsername(req.getUsername());
        updateUser.setPassword(req.getPassword());
        updateUser.setNoTelp(req.getNoTelp());
        return userRepository.save(updateUser);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
