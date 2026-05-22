package cl.potion.api.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit Tests for UserEntity
 *
 * @author Test Suite
 * @version 1.0.0
 */
@DisplayName("UserEntity Tests")
class UserEntityTest {

    private UserEntity userEntity;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
        userEntity = new UserEntity();
    }

    @Test
    @DisplayName("Should create UserEntity with default constructor")
    void testDefaultConstructor() {
        assertNotNull(userEntity);
        assertNull(userEntity.getId());
        assertNull(userEntity.getUsername());
        assertNull(userEntity.getPassword());
        assertNull(userEntity.getEmail());
        assertNull(userEntity.getCreateAt());
        assertNull(userEntity.getUpdatedAt());
        assertNull(userEntity.getActive());
    }

    @Test
    @DisplayName("Should create UserEntity with all arguments constructor")
    void testFullConstructor() {
        BigInteger userId = BigInteger.valueOf(1L);

        UserEntity user = new UserEntity(
                userId, "testuser", "password123", "test@example.com",
                now, now, true);

        assertEquals(userId, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("test@example.com", user.getEmail());
        assertEquals(now, user.getCreateAt());
        assertEquals(now, user.getUpdatedAt());
        assertTrue(user.getActive());
    }

    @Test
    @DisplayName("Should create UserEntity using Builder pattern")
    void testBuilder() {
        BigInteger userId = BigInteger.valueOf(2L);

        UserEntity user = UserEntity.builder()
                .id(userId)
                .username("builderuser")
                .password("secure123")
                .email("builder@example.com")
                .createAt(now)
                .updatedAt(now)
                .active(true)
                .build();

        assertEquals(userId, user.getId());
        assertEquals("builderuser", user.getUsername());
        assertEquals("secure123", user.getPassword());
        assertEquals("builder@example.com", user.getEmail());
        assertEquals(now, user.getCreateAt());
        assertEquals(now, user.getUpdatedAt());
        assertTrue(user.getActive());
    }

    @Test
    @DisplayName("Should set and get username")
    void testSetAndGetUsername() {
        String username = "newuser";
        userEntity.setUsername(username);

        assertEquals(username, userEntity.getUsername());
    }

    @Test
    @DisplayName("Should set and get email")
    void testSetAndGetEmail() {
        String email = "user@example.com";
        userEntity.setEmail(email);

        assertEquals(email, userEntity.getEmail());
    }

    @Test
    @DisplayName("Should set and get password")
    void testSetAndGetPassword() {
        String password = "hashedpassword";
        userEntity.setPassword(password);

        assertEquals(password, userEntity.getPassword());
    }

    @Test
    @DisplayName("Should set and get active status")
    void testSetAndGetActive() {
        userEntity.setActive(true);
        assertTrue(userEntity.getActive());

        userEntity.setActive(false);
        assertFalse(userEntity.getActive());
    }

    @Test
    @DisplayName("Should set and get creation timestamp")
    void testSetAndGetCreateAt() {
        LocalDateTime createTime = LocalDateTime.of(2026, 5, 20, 10, 30);
        userEntity.setCreateAt(createTime);

        assertEquals(createTime, userEntity.getCreateAt());
    }

    @Test
    @DisplayName("Should set and get update timestamp")
    void testSetAndGetUpdatedAt() {
        LocalDateTime updateTime = LocalDateTime.of(2026, 5, 20, 15, 45);
        userEntity.setUpdatedAt(updateTime);

        assertEquals(updateTime, userEntity.getUpdatedAt());
    }

    @Test
    @DisplayName("Should set and get user id")
    void testSetAndGetId() {
        BigInteger userId = BigInteger.valueOf(123L);
        userEntity.setId(userId);

        assertEquals(userId, userEntity.getId());
    }

    @Test
    @DisplayName("Should generate toString representation")
    void testToString() {
        userEntity.setUsername("testuser");
        userEntity.setEmail("test@example.com");

        String toString = userEntity.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("testuser") || toString.contains("UserEntity"));
    }

    @Test
    @DisplayName("Should handle all fields together in builder")
    void testCompleteUserEntity() {
        BigInteger userId = BigInteger.valueOf(999L);
        LocalDateTime createdAt = LocalDateTime.of(2026, 5, 1, 10, 0);
        LocalDateTime updatedAt = LocalDateTime.of(2026, 5, 20, 14, 30);

        UserEntity completeUser = UserEntity.builder()
                .id(userId)
                .username("completuser")
                .password("complexpass123!")
                .email("complete@potion.cl")
                .createAt(createdAt)
                .updatedAt(updatedAt)
                .active(true)
                .build();

        // Verify all fields
        assertEquals(userId, completeUser.getId());
        assertEquals("completuser", completeUser.getUsername());
        assertEquals("complexpass123!", completeUser.getPassword());
        assertEquals("complete@potion.cl", completeUser.getEmail());
        assertEquals(createdAt, completeUser.getCreateAt());
        assertEquals(updatedAt, completeUser.getUpdatedAt());
        assertTrue(completeUser.getActive());
    }
}
