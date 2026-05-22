package cl.potion.api.service.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigInteger;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import cl.potion.api.entity.UserEntity;
import cl.potion.api.exception.ServiceException;
import cl.potion.api.repository.UserRepository;
import cl.potion.api.dto.request.UserRequest;
import cl.potion.api.dto.response.DefaultResponse;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserServiceImpl Tests")
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity activeUser;
    private UserEntity inactiveUser;
    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();

        activeUser = UserEntity.builder()
                .id(BigInteger.ONE)
                .username("activeuser")
                .password("hashedpassword")
                .email("active@example.com")
                .createAt(now)
                .updatedAt(now)
                .active(true)
                .build();

        inactiveUser = UserEntity.builder()
                .id(BigInteger.TWO)
                .username("inactiveuser")
                .password("hashedpassword")
                .email("inactive@example.com")
                .createAt(now)
                .updatedAt(now)
                .active(false)
                .build();

        userRequest = UserRequest.builder()
                .username("newuser")
                .password("password123")
                .email("new@example.com")
                .build();
    }

    // --- registerUser ---

    @Test
    @DisplayName("registerUser should save user and return success response")
    void testRegisterUserSuccess() throws ServiceException {
        when(repository.save(any(UserEntity.class))).thenReturn(activeUser);

        DefaultResponse response = userService.registerUser(userRequest);

        assertNotNull(response);
        assertEquals("200", response.getCode());
        assertEquals("USER REGISTERED", response.getMessage());
        verify(repository, times(1)).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("registerUser should throw ServiceException on duplicate username")
    void testRegisterUserDuplicateUsername() {
        when(repository.save(any(UserEntity.class)))
                .thenThrow(new DataIntegrityViolationException(
                        "duplicate key value violates unique constraint Detail: Key (username)=(newuser) already exists."));

        ServiceException exception = assertThrows(ServiceException.class,
                () -> userService.registerUser(userRequest));

        assertEquals("USER 'USERNAME' ALREADY EXISTS", exception.getMessage());
    }

    @Test
    @DisplayName("registerUser should throw ServiceException on duplicate email")
    void testRegisterUserDuplicateEmail() {
        when(repository.save(any(UserEntity.class)))
                .thenThrow(new DataIntegrityViolationException(
                        "duplicate key value violates unique constraint Detail: Key (email)=(new@example.com) already exists."));

        ServiceException exception = assertThrows(ServiceException.class,
                () -> userService.registerUser(userRequest));

        assertEquals("USER 'EMAIL' ALREADY EXISTS", exception.getMessage());
    }

    @Test
    @DisplayName("registerUser should throw ServiceException on generic exception")
    void testRegisterUserGenericException() {
        when(repository.save(any(UserEntity.class)))
                .thenThrow(new RuntimeException("Database connection failed"));

        ServiceException exception = assertThrows(ServiceException.class,
                () -> userService.registerUser(userRequest));

        assertEquals("Database connection failed", exception.getMessage());
    }

    // --- getAllUsers ---

    @Test
    @DisplayName("getAllUsers should return paginated active users")
    void testGetAllUsersSuccess() throws ServiceException {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<UserEntity> page = new PageImpl<>(List.of(activeUser));
        when(repository.searchAllByActiveTrue(pageRequest)).thenReturn(page);

        Page<UserEntity> result = userService.getAllUsers(pageRequest);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("activeuser", result.getContent().get(0).getUsername());
    }

    @Test
    @DisplayName("getAllUsers should throw ServiceException on error")
    void testGetAllUsersException() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        when(repository.searchAllByActiveTrue(pageRequest))
                .thenThrow(new RuntimeException("DB error"));

        assertThrows(ServiceException.class, () -> userService.getAllUsers(pageRequest));
    }

    // --- searchByUsername ---

    @Test
    @DisplayName("searchByUsername should return user when found and active")
    void testSearchByUsernameSuccess() throws ServiceException {
        when(repository.searchByUsername("activeuser")).thenReturn(activeUser);

        DefaultResponse response = userService.searchByUsername("activeuser");

        assertNotNull(response);
        assertEquals("200", response.getCode());
        assertEquals("USER SEARCHED", response.getMessage());
        assertNotNull(response.getResponse());
    }

    @Test
    @DisplayName("searchByUsername should throw when user not found")
    void testSearchByUsernameNotFound() {
        when(repository.searchByUsername("unknown")).thenReturn(null);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> userService.searchByUsername("unknown"));

        assertEquals("USER NOT FOUND", exception.getMessage());
    }

    @Test
    @DisplayName("searchByUsername should throw when user is deactivated")
    void testSearchByUsernameDeactivated() {
        when(repository.searchByUsername("inactiveuser")).thenReturn(inactiveUser);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> userService.searchByUsername("inactiveuser"));

        assertEquals("CANT SEARCH A DEACTIVATED USER", exception.getMessage());
    }

    // --- deactivateUser ---

    @Test
    @DisplayName("deactivateUser should deactivate an active user")
    void testDeactivateUserSuccess() throws ServiceException {
        when(repository.searchById(BigInteger.ONE)).thenReturn(activeUser);
        when(repository.save(any(UserEntity.class))).thenReturn(activeUser);

        DefaultResponse response = userService.deactivateUser(BigInteger.ONE);

        assertNotNull(response);
        assertEquals("200", response.getCode());
        assertEquals("USER DEACTIVATED", response.getMessage());
        verify(repository).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("deactivateUser should throw when user not found")
    void testDeactivateUserNotFound() {
        when(repository.searchById(BigInteger.valueOf(999))).thenReturn(null);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> userService.deactivateUser(BigInteger.valueOf(999)));

        assertEquals("USER NOT FOUND", exception.getMessage());
    }

    @Test
    @DisplayName("deactivateUser should throw when user already deactivated")
    void testDeactivateUserAlreadyDeactivated() {
        when(repository.searchById(BigInteger.TWO)).thenReturn(inactiveUser);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> userService.deactivateUser(BigInteger.TWO));

        assertEquals("USER ALREADY DEACTIVATED", exception.getMessage());
    }

    // --- activateUser ---

    @Test
    @DisplayName("activateUser should activate a deactivated user")
    void testActivateUserSuccess() throws ServiceException {
        when(repository.searchById(BigInteger.TWO)).thenReturn(inactiveUser);
        when(repository.save(any(UserEntity.class))).thenReturn(inactiveUser);

        DefaultResponse response = userService.activateUser(BigInteger.TWO);

        assertNotNull(response);
        assertEquals("200", response.getCode());
        assertEquals("USER ACTIVATED", response.getMessage());
        verify(repository).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("activateUser should throw when user not found")
    void testActivateUserNotFound() {
        when(repository.searchById(BigInteger.valueOf(999))).thenReturn(null);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> userService.activateUser(BigInteger.valueOf(999)));

        assertEquals("USER NOT FOUND", exception.getMessage());
    }

    @Test
    @DisplayName("activateUser should throw when user already activated")
    void testActivateUserAlreadyActivated() {
        when(repository.searchById(BigInteger.ONE)).thenReturn(activeUser);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> userService.activateUser(BigInteger.ONE));

        assertEquals("USER ALREADY ACTIVATED", exception.getMessage());
    }

    // --- updateUser ---

    @Test
    @DisplayName("updateUser should update an active user")
    void testUpdateUserSuccess() throws ServiceException {
        when(repository.searchById(BigInteger.ONE)).thenReturn(activeUser);
        when(repository.save(any(UserEntity.class))).thenReturn(activeUser);

        UserRequest updateRequest = UserRequest.builder()
                .username("updateduser")
                .email("updated@example.com")
                .build();

        DefaultResponse response = userService.updateUser(BigInteger.ONE, updateRequest);

        assertNotNull(response);
        assertEquals("200", response.getCode());
        assertEquals("USER UPDATED", response.getMessage());
        assertNotNull(response.getResponse());
    }

    @Test
    @DisplayName("updateUser should throw when user not found")
    void testUpdateUserNotFound() {
        when(repository.searchById(BigInteger.valueOf(999))).thenReturn(null);

        assertThrows(ServiceException.class,
                () -> userService.updateUser(BigInteger.valueOf(999), userRequest));
    }

    @Test
    @DisplayName("updateUser should throw when user is deactivated")
    void testUpdateUserDeactivated() {
        when(repository.searchById(BigInteger.TWO)).thenReturn(inactiveUser);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> userService.updateUser(BigInteger.TWO, userRequest));

        assertEquals("CANT UPDATE A DEACTIVATED USER", exception.getMessage());
    }

    @Test
    @DisplayName("updateUser should throw on duplicate key violation")
    void testUpdateUserDuplicateKey() {
        when(repository.searchById(BigInteger.ONE)).thenReturn(activeUser);
        when(repository.save(any(UserEntity.class)))
                .thenThrow(new DataIntegrityViolationException(
                        "duplicate key value violates unique constraint Detail: Key (email)=(dup@example.com) already exists."));

        ServiceException exception = assertThrows(ServiceException.class,
                () -> userService.updateUser(BigInteger.ONE, userRequest));

        assertEquals("USER 'EMAIL' ALREADY EXISTS", exception.getMessage());
    }

    @Test
    @DisplayName("updateUser should keep existing values when request fields are null")
    void testUpdateUserPartialUpdate() throws ServiceException {
        when(repository.searchById(BigInteger.ONE)).thenReturn(activeUser);
        when(repository.save(any(UserEntity.class))).thenReturn(activeUser);

        UserRequest partialRequest = UserRequest.builder().build();

        DefaultResponse response = userService.updateUser(BigInteger.ONE, partialRequest);

        assertNotNull(response);
        assertEquals("200", response.getCode());
    }
}
