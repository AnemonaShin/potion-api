package cl.potion.api.response;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cl.potion.api.dto.response.ExceptionResponse;

@DisplayName("ExceptionResponse Tests")
class ExceptionResponseTest {

    @Test
    @DisplayName("Should create ExceptionResponse with default constructor")
    void testDefaultConstructor() {
        ExceptionResponse response = new ExceptionResponse();

        assertNull(response.getCode());
        assertNull(response.getMessage());
        assertNull(response.getTimestamp());
    }

    @Test
    @DisplayName("Should create ExceptionResponse with all arguments constructor")
    void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        ExceptionResponse response = new ExceptionResponse("404", "Not Found", now);

        assertEquals("404", response.getCode());
        assertEquals("Not Found", response.getMessage());
        assertEquals(now, response.getTimestamp());
    }

    @Test
    @DisplayName("Should create ExceptionResponse using Builder pattern")
    void testBuilder() {
        LocalDateTime now = LocalDateTime.now();
        ExceptionResponse response = ExceptionResponse.builder()
                .code("409")
                .message("Conflict")
                .timestamp(now)
                .build();

        assertEquals("409", response.getCode());
        assertEquals("Conflict", response.getMessage());
        assertEquals(now, response.getTimestamp());
    }

    @Test
    @DisplayName("Should set and get code")
    void testSetAndGetCode() {
        ExceptionResponse response = new ExceptionResponse();
        response.setCode("500");
        assertEquals("500", response.getCode());
    }

    @Test
    @DisplayName("Should set and get message")
    void testSetAndGetMessage() {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage("Internal Server Error");
        assertEquals("Internal Server Error", response.getMessage());
    }

    @Test
    @DisplayName("Should set and get timestamp")
    void testSetAndGetTimestamp() {
        ExceptionResponse response = new ExceptionResponse();
        LocalDateTime ts = LocalDateTime.of(2026, 5, 20, 12, 0);
        response.setTimestamp(ts);
        assertEquals(ts, response.getTimestamp());
    }
}
