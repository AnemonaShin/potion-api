package cl.potion.api;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("ApiApplication Tests")
class ApiApplicationTests {

    @Test
    @DisplayName("Context should load successfully")
    void contextLoads() {
        assertDoesNotThrow(() -> {});
    }
}
