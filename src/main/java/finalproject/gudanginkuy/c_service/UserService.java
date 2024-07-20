package finalproject.gudanginkuy.c_service;

import finalproject.gudanginkuy.a_model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<User> getAll (String username, Pageable pageable);
    User getOne (Integer id);
//    User create (User request);
//    //
    User updategantiusername (Integer id, User request);
    User updategantipassword (Integer id, User request);
    void delete (Integer id);
}
