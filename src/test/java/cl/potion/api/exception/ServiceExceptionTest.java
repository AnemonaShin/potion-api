package cl.potion.api.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("ServiceException Tests")
class ServiceExceptionTest {

    @Test
    @DisplayName("Constructor with HttpStatus and message should set fields correctly")
    void testConstructorWithStatusAndMessage() {
        ServiceException ex = new ServiceException(HttpStatus.BAD_REQUEST, "Bad request");

        assertEquals(HttpStatus.BAD_REQUEST, ex.getHttpStatus());
        assertEquals("400", ex.getCode());
        assertEquals("Bad request", ex.getMessage());
        assertNotNull(ex.getTimeStamp());
    }

    @Test
    @DisplayName("Constructor with HttpStatus, code and message should set fields correctly")
    void testConstructorWithStatusCodeAndMessage() {
        ServiceException ex = new ServiceException(HttpStatus.CONFLICT, "409", "Conflict occurred");

        assertEquals(HttpStatus.CONFLICT, ex.getHttpStatus());
        assertEquals("409", ex.getCode());
        assertEquals("Conflict occurred", ex.getMessage());
        assertNotNull(ex.getTimeStamp());
    }

    @Test
    @DisplayName("Constructor with ExceptionList should set fields correctly")
    void testConstructorWithExceptionList() {
        ServiceException ex = new ServiceException(ExceptionList.UNFD);

        assertEquals(HttpStatus.NOT_FOUND, ex.getHttpStatus());
        assertEquals("404", ex.getCode());
        assertEquals("USER NOT FOUND", ex.getMessage());
        assertNotNull(ex.getTimeStamp());
    }

    @Test
    @DisplayName("Should set correct code from INTERNAL_SERVER_ERROR")
    void testInternalServerError() {
        ServiceException ex = new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Something broke");

        assertEquals("500", ex.getCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getHttpStatus());
    }

    @Test
    @DisplayName("Constructor with ExceptionList USAA should set CONFLICT status")
    void testConstructorWithUsaa() {
        ServiceException ex = new ServiceException(ExceptionList.USAA);

        assertEquals(HttpStatus.CONFLICT, ex.getHttpStatus());
        assertEquals("409", ex.getCode());
        assertEquals("USER ALREADY ACTIVATED", ex.getMessage());
    }
}
