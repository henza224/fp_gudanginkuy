package finalproject.gudanginkuy.c_service;

import finalproject.gudanginkuy.a_model.Role;
import finalproject.gudanginkuy.a_model.User;
import finalproject.gudanginkuy.b_repository.UserRepository;
import finalproject.gudanginkuy.c_service.impl.UserServiceImpl;
import finalproject.gudanginkuy.utils.specification.UserSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

//    @Test
//    public void createUser(){
//        User user = mock(User.class);
//        User user1 = User.builder()
//                .username("budi")
//                .password("1234")
//                .role(Role.ROLE_USER)
//                .build();
//        when(userRepository.save(any(User.class)))
//                .thenReturn(user);
//        User addUser = userService.create(user1);
//
//        assertThat(addUser).isNotNull();
//    }
    @Test
    public void getByIdTest(){
        User user = mock(User.class);
        when(userRepository.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(user));
        User foundUser = userService.getOne(1);

        assertThat(foundUser).isNotNull();
    }
    @Test
    public void getAll(){
        Pageable pageable = PageRequest.of(0,10);
        Page<User> addUser = new PageImpl<>(Collections.emptyList());

        when(userRepository.findAll(pageable)).thenReturn(addUser);

        Page<User> result = userService.getAll("test",pageable);

        assertEquals(0,result.getTotalElements());
    }
    @Test
    public void updateUserTest(){
        User user = mock(User.class);
        User user1 = User.builder()
                .username("budi")
                .password("1234")
                .role(Role.ROLE_USER)
                .build();
        when(userRepository.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(user));
        when(userRepository.save(any(User.class)))
                .thenReturn(user1);
        User updatedUser = userService.updategantiusername(1,user1);
        User updatePw = userService.updategantipassword(1,user1);

        assertThat(updatedUser).isNotNull();
        assertThat(updatePw).isNotNull();
    }
    @Test
    public void deleteUserTest(){
        User user= mock(User.class);
        assertAll(()->userService.delete(1));
    }
}
