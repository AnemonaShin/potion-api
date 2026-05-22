package cl.potion.api.response;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cl.potion.api.dto.response.DefaultResponse;

@DisplayName("DefaultResponse Tests")
class DefaultResponseTest {

    @Test
    @DisplayName("Should create DefaultResponse with default constructor")
    void testDefaultConstructor() {
        DefaultResponse response = new DefaultResponse();

        assertNull(response.getCode());
        assertNull(response.getMessage());
        assertNull(response.getResponse());
    }

    @Test
    @DisplayName("Should create DefaultResponse with all arguments constructor")
    void testAllArgsConstructor() {
        Object data = "some data";
        DefaultResponse response = new DefaultResponse("200", "OK", data);

        assertEquals("200", response.getCode());
        assertEquals("OK", response.getMessage());
        assertEquals(data, response.getResponse());
    }

    @Test
    @DisplayName("Should create DefaultResponse using Builder pattern")
    void testBuilder() {
        DefaultResponse response = DefaultResponse.builder()
                .code("201")
                .message("Created")
                .response("user data")
                .build();

        assertEquals("201", response.getCode());
        assertEquals("Created", response.getMessage());
        assertEquals("user data", response.getResponse());
    }

    @Test
    @DisplayName("Should set and get code")
    void testSetAndGetCode() {
        DefaultResponse response = new DefaultResponse();
        response.setCode("404");
        assertEquals("404", response.getCode());
    }

    @Test
    @DisplayName("Should set and get message")
    void testSetAndGetMessage() {
        DefaultResponse response = new DefaultResponse();
        response.setMessage("Not Found");
        assertEquals("Not Found", response.getMessage());
    }

    @Test
    @DisplayName("Should set and get response object")
    void testSetAndGetResponse() {
        DefaultResponse response = new DefaultResponse();
        Object data = new Object();
        response.setResponse(data);
        assertEquals(data, response.getResponse());
    }

    @Test
    @DisplayName("Should handle null response field")
    void testNullResponse() {
        DefaultResponse response = DefaultResponse.builder()
                .code("200")
                .message("OK")
                .build();

        assertEquals("200", response.getCode());
        assertEquals("OK", response.getMessage());
        assertNull(response.getResponse());
    }
}
