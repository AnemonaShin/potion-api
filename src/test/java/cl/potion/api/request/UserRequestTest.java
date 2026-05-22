package cl.potion.api.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cl.potion.api.dto.request.UserRequest;

@DisplayName("UserRequest Tests")
class UserRequestTest {

    @Test
    @DisplayName("Should create UserRequest with default constructor")
    void testDefaultConstructor() {
        UserRequest request = new UserRequest();

        assertNull(request.getUsername());
        assertNull(request.getPassword());
        assertNull(request.getEmail());
    }

    @Test
    @DisplayName("Should create UserRequest with all arguments constructor")
    void testAllArgsConstructor() {
        UserRequest request = new UserRequest("testuser", "password123", "test@example.com");

        assertEquals("testuser", request.getUsername());
        assertEquals("password123", request.getPassword());
        assertEquals("test@example.com", request.getEmail());
    }

    @Test
    @DisplayName("Should create UserRequest using Builder pattern")
    void testBuilder() {
        UserRequest request = UserRequest.builder()
                .username("builderuser")
                .password("builderpass")
                .email("builder@example.com")
                .build();

        assertEquals("builderuser", request.getUsername());
        assertEquals("builderpass", request.getPassword());
        assertEquals("builder@example.com", request.getEmail());
    }

    @Test
    @DisplayName("Should set and get username")
    void testSetAndGetUsername() {
        UserRequest request = new UserRequest();
        request.setUsername("newuser");
        assertEquals("newuser", request.getUsername());
    }

    @Test
    @DisplayName("Should set and get password")
    void testSetAndGetPassword() {
        UserRequest request = new UserRequest();
        request.setPassword("newpass");
        assertEquals("newpass", request.getPassword());
    }

    @Test
    @DisplayName("Should set and get email")
    void testSetAndGetEmail() {
        UserRequest request = new UserRequest();
        request.setEmail("new@example.com");
        assertEquals("new@example.com", request.getEmail());
    }
}
