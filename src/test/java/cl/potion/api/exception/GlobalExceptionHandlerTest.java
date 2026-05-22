package cl.potion.api.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import cl.potion.api.dto.response.ExceptionResponse;

@DisplayName("GlobalExceptionHandler Tests")
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    @DisplayName("Should handle ServiceException and return correct status and body")
    void testHandleServiceException() {
        ServiceException serviceException = new ServiceException(HttpStatus.NOT_FOUND, "User not found");

        ResponseEntity<ExceptionResponse> response = handler.handleGlobalException(serviceException);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("404", response.getBody().getCode());
        assertEquals("User not found", response.getBody().getMessage());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    @DisplayName("Should handle ServiceException with ExceptionList")
    void testHandleServiceExceptionFromList() {
        ServiceException serviceException = new ServiceException(ExceptionList.USAD);

        ResponseEntity<ExceptionResponse> response = handler.handleGlobalException(serviceException);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("409", response.getBody().getCode());
        assertEquals("USER ALREADY DEACTIVATED", response.getBody().getMessage());
    }

    @Test
    @DisplayName("Should handle ServiceException with custom code")
    void testHandleServiceExceptionWithCustomCode() {
        ServiceException serviceException = new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "500",
                "Internal error");

        ResponseEntity<ExceptionResponse> response = handler.handleGlobalException(serviceException);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("500", response.getBody().getCode());
        assertEquals("Internal error", response.getBody().getMessage());
    }
}
