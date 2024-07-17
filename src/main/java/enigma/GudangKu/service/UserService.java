package enigma.GudangKu.service;

import enigma.GudangKu.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User create(User req);
    List<User> getAll(String username, Integer noTelp);
    User getOne(Integer id);
    User update(Integer id,User req);
    void delete (Integer id);
}
