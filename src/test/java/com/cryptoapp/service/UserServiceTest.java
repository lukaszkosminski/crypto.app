package com.cryptoapp.service;

import com.cryptoapp.dto.UserDTO;
import com.cryptoapp.dto.WalletDTO;
import com.cryptoapp.dto.mapper.UserMapper;
import com.cryptoapp.dto.mapper.ValueMapper;
import com.cryptoapp.model.Currency;
import com.cryptoapp.model.User;
import com.cryptoapp.model.Value;
import com.cryptoapp.model.Wallet;
import com.cryptoapp.repository.CurrencyRepo;
import com.cryptoapp.repository.UserRepo;
import com.cryptoapp.repository.ValueRepo;
import com.cryptoapp.repository.WalletRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private WalletRepo walletRepo;

    @Mock
    private ValueRepo valueRepo;
    @Mock
    private ValueService valueService;

    @Mock
    private WalletService walletService;
    @Mock
    private CurrencyService currencyService;

    @Mock
    private CurrencyRepo currencyRepo;

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


        when(userRepo.findByLogin(userAuth.getLogin())).thenReturn(user);

        // when
        UserDTO updatedUserDTO = userService.changeEmail(userDTO, userAuth);

        // then
        assertEquals(userAuth.getEmail(), updatedUserDTO.getEmail());
        verify(userRepo, times(1)).findByLogin(userAuth.getLogin());
        verify(userRepo, times(1)).save(user);
    }
    @Test
    @DisplayName("Should change user password")
    public void testChangePassword() {
        // Setup
        User userAuth = new User();
        userAuth.setPassword("oldPassword");
        UserDTO userDTO = new UserDTO();
        String newPassword ="newPassword";
        userDTO.setPassword(newPassword);


        User user = new User();
        user.setPassword("oldPassword");
        when(userRepo.findByLogin(userAuth.getLogin())).thenReturn(user);

        // Execution
        UserDTO result = userService.changePassword(userAuth, userDTO);

        // Verification
        verify(userRepo, times(1)).findByLogin(userAuth.getLogin());
        verify(userRepo, times(1)).save(user);

        assertTrue(BCrypt.checkpw(newPassword, result.getPassword()));

    }
    @Test
    @DisplayName("Should change user null password")
    public void testChangePasswordWithNull() {
        // given
        User user = new User();
        user.setPassword("oldPassword");
        UserDTO userDTO = new UserDTO();

        when(userRepo.findByLogin(user.getLogin())).thenReturn(user);

        // when, then
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            userService.changePassword(user, userDTO);
        });

        assertThat(ex.getMessage()).isEqualTo("Password cannot be null");

        verify(userRepo, never()).save(any(User.class));
    }
@Test
@DisplayName("Should delete user and associated data")
    public void deleteUserAndAssociatedData(){
        //given
        Long idUser = 1L;
        User user = new User();
        user.setId(idUser);

        List<Wallet> wallets = Arrays.asList(
                new Wallet(),
                new Wallet(),
                new Wallet()
        );
        when(userRepo.findById(idUser)).thenReturn(Optional.of(user));
        when(walletRepo.findAllByUser(user)).thenReturn(wallets);

        //when
        userService.deleteUser(idUser);

        //then
        verify(valueRepo, times(wallets.size())).deleteByWallet(any(Wallet.class));
        verify(walletRepo).deleteByUser(user);
        verify(userRepo).delete(user);
    }

    @Test
    @DisplayName("Should throw NoSuchElementException when user not found")
    public void deleteUser_UserNotFound_ThrowsException() {
        // given
        Long idUser = 1L;
        when(userRepo.findById(idUser)).thenReturn(Optional.empty());

        // when + then
        Assertions.assertThrows(NoSuchElementException.class, () -> userService.deleteUser(idUser));
    }

    @DisplayName("Add wallet to user")
    @Test
    public void addWalletToUser_Success() {
        // given
        User user = new User();
        user.setLogin("john_doe");
        user.setPassword("password");
        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setName("My Wallet");


        when(userRepo.findByLogin(user.getLogin())).thenReturn(user);
        when(walletService.save(any(Wallet.class))).thenAnswer(invocation -> {
            Wallet wallet = invocation.getArgument(0);
            wallet.setIdWallet(1L);
            return wallet;
        });

        // when
        WalletDTO result = userService.addWalletToUser(user, walletDTO);

        // then
        assertNotNull(result);
        assertEquals(walletDTO.getName(), result.getName());
        verify(userRepo).findByLogin(user.getLogin());
        verify(walletService).save(any(Wallet.class));
    }
    @Test
    @DisplayName("Test get user")
    public void testGetUser() {
        // given
        User user = new User();
        user.setId(1L);
        user.setLogin("John");

        // when
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));
        User result = userService.getUser(1L);

        // then
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getLogin(), result.getLogin());
    }

    @Test
    @DisplayName("Test getWalletsByUser method")
    public void testGetWalletsByUser() {
        // given
        User userAuth = new User();
        userAuth.setLogin("John");
        Wallet wallet1 = new Wallet();
        wallet1.setIdWallet(1L);
        wallet1.setName("Test Wallet1");
        Wallet wallet2 = new Wallet();
        wallet2.setIdWallet(2L);
        wallet2.setName("Test Wallet2");

        List<Wallet> walletList = new ArrayList<>();
        walletList.add(wallet1);
        walletList.add(wallet2);
        User user = new User();
        user.setWalletList(walletList);
        user.setLogin("Joe");

        when(userRepo.findByLogin(userAuth.getLogin())).thenReturn(user);

        // when
        List<WalletDTO> walletDTOList = userService.getWalletsByUser(userAuth);

        // then
        Assertions.assertEquals(walletList.size(), walletDTOList.size());

    }
    @Test
    @DisplayName("Test create user with prefered currency")
    public void testCreateUserWithPreferredCurrency() throws Exception {
        // given
        User user = new User();
        user.setEmail("test@test.com");
        user.setLogin("testuser");
        user.setPreferredCurrency("USD");
        when(userRepo.save(any(User.class))).thenReturn(user);


        Wallet wallet = new Wallet();
        wallet.setIdWallet(1L);
        wallet.setName("default");
        when(walletService.save(any(Wallet.class))).thenReturn(wallet);

        Currency currency = new Currency();
        currency.setId(1L);
        when(currencyService.save(any(Currency.class))).thenReturn(currency);

        Value value = new Value();
        value.setId(1L);
        when(valueService.save(any(com.cryptoapp.dto.ValueDTO.class))).thenReturn(ValueMapper.mapToDTO(value));

        // When
        UserDTO result = userService.createUserWithPreferredCurrency(UserMapper.mapToDTO(user));

        // Then
        assertNotNull(result);
        assertEquals(user.getLogin(), result.getLogin());

        assertEquals(user.getPreferredCurrency(), result.getPreferredCurrency());


}}