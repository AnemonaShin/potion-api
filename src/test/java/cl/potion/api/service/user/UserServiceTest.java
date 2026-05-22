package cl.potion.api.service.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UserService Interface Tests")
class UserServiceTest {

    @Test
    @DisplayName("UserServiceImpl should implement UserService interface")
    void testImplementsInterface() {
        assertTrue(UserService.class.isAssignableFrom(UserServiceImpl.class));
    }

    @Test
    @DisplayName("UserService should be an interface")
    void testIsInterface() {
        assertTrue(UserService.class.isInterface());
    }
}
