package com.cryptoapp.service;

import com.cryptoapp.dto.UserDTO;
import com.cryptoapp.model.User;
import com.cryptoapp.repository.UserRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should change user email")
    public void testChangeEmail() {
        // given
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("old_email@example.com");

        User userAuth = new User();
        userAuth.setLogin("user_login");
        userAuth.setEmail("new_email@example.com");

        User user = new User();
        user.setId(1L);
        user.setLogin(userAuth.getLogin());
        user.setEmail(userDTO.getEmail());


        Mockito.when(userRepo.findByLogin(userAuth.getLogin())).thenReturn(user);

        // when
        UserDTO updatedUserDTO = userService.changeEmail(userDTO, userAuth);

        // then
        assertEquals(userAuth.getEmail(), updatedUserDTO.getEmail());
        Mockito.verify(userRepo, Mockito.times(1)).findByLogin(userAuth.getLogin());
        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }
}