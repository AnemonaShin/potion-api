package cl.potion.api.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import cl.potion.api.entity.UserEntity;

@DataJpaTest
@DisplayName("UserRepository Tests")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private UserEntity savedUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        LocalDateTime now = LocalDateTime.now();

        UserEntity user = UserEntity.builder()
                .username("testuser")
                .password("hashedpassword")
                .email("test@example.com")
                .createAt(now)
                .updatedAt(now)
                .active(true)
                .build();

        savedUser = userRepository.save(user);
    }

    @Test
    @DisplayName("Should search user by username")
    void testSearchByUsername() {
        UserEntity found = userRepository.searchByUsername("testuser");

        assertNotNull(found);
        assertEquals("testuser", found.getUsername());
        assertEquals("test@example.com", found.getEmail());
    }

    @Test
    @DisplayName("Should return null when username not found")
    void testSearchByUsernameNotFound() {
        UserEntity found = userRepository.searchByUsername("nonexistent");

        assertNull(found);
    }

    @Test
    @DisplayName("Should search user by id")
    void testSearchById() {
        UserEntity found = userRepository.searchById(savedUser.getId());

        assertNotNull(found);
        assertEquals(savedUser.getId(), found.getId());
        assertEquals("testuser", found.getUsername());
    }

    @Test
    @DisplayName("Should return null when id not found")
    void testSearchByIdNotFound() {
        UserEntity found = userRepository.searchById(BigInteger.valueOf(99999));

        assertNull(found);
    }

    @Test
    @DisplayName("Should search all active users with pagination")
    void testSearchAllByActiveTrue() {
        LocalDateTime now = LocalDateTime.now();

        UserEntity inactiveUser = UserEntity.builder()
                .username("inactiveuser")
                .password("hashedpassword")
                .email("inactive@example.com")
                .createAt(now)
                .updatedAt(now)
                .active(false)
                .build();
        userRepository.save(inactiveUser);

        Page<UserEntity> activePage = userRepository.searchAllByActiveTrue(PageRequest.of(0, 10));

        assertNotNull(activePage);
        assertEquals(1, activePage.getTotalElements());
        assertTrue(activePage.getContent().stream().allMatch(UserEntity::getActive));
    }

    @Test
    @DisplayName("Should return empty page when no active users")
    void testSearchAllByActiveTrueEmpty() {
        savedUser.setActive(false);
        userRepository.save(savedUser);

        Page<UserEntity> activePage = userRepository.searchAllByActiveTrue(PageRequest.of(0, 10));

        assertNotNull(activePage);
        assertEquals(0, activePage.getTotalElements());
    }
}
