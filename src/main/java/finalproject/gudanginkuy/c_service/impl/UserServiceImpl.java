package finalproject.gudanginkuy.c_service.impl;

import finalproject.gudanginkuy.a_model.User;
import finalproject.gudanginkuy.b_repository.UserRepository;
import finalproject.gudanginkuy.c_service.UserService;
import finalproject.gudanginkuy.utils.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    public Page<User> getAll(String username, Pageable pageable) {
        Specification<User> specification = UserSpecification.getSpecification(username);
        return userRepository.findAll(specification, pageable);
    }


    @Override
    public User getOne(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("User Not Found")
                );
    }

    @Override
    public User updategantiusername(Integer id, User request) {
        User user = this.getOne(id);
        user.setUsername(request.getUsername());
        userRepository.save(user);
        return user;
    }

    @Override
    public User updategantipassword(Integer id, User request) {
        User user = this.getOne(id);
        user.setPassword(request.getPassword());
        userRepository.save(user);
        return user;
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

}