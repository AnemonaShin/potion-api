package cl.potion.api.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("ExceptionList Enum Tests")
class ExceptionListTest {

    @Test
    @DisplayName("UNFD should have NOT_FOUND status")
    void testUnfd() {
        assertEquals(HttpStatus.NOT_FOUND, ExceptionList.UNFD.getHttpStatus());
        assertEquals("USER NOT FOUND", ExceptionList.UNFD.getMessage());
    }

    @Test
    @DisplayName("USAA should have CONFLICT status")
    void testUsaa() {
        assertEquals(HttpStatus.CONFLICT, ExceptionList.USAA.getHttpStatus());
        assertEquals("USER ALREADY ACTIVATED", ExceptionList.USAA.getMessage());
    }

    @Test
    @DisplayName("USAD should have CONFLICT status")
    void testUsad() {
        assertEquals(HttpStatus.CONFLICT, ExceptionList.USAD.getHttpStatus());
        assertEquals("USER ALREADY DEACTIVATED", ExceptionList.USAD.getMessage());
    }

    @Test
    @DisplayName("USDCU should have NOT_MODIFIED status")
    void testUsdcu() {
        assertEquals(HttpStatus.NOT_MODIFIED, ExceptionList.USDCU.getHttpStatus());
        assertEquals("CANT UPDATE A DEACTIVATED USER", ExceptionList.USDCU.getMessage());
    }

    @Test
    @DisplayName("USDCS should have CONFLICT status")
    void testUsdcs() {
        assertEquals(HttpStatus.CONFLICT, ExceptionList.USDCS.getHttpStatus());
        assertEquals("CANT SEARCH A DEACTIVATED USER", ExceptionList.USDCS.getMessage());
    }

    @Test
    @DisplayName("USEAT should have CONFLICT status")
    void testUseat() {
        assertEquals(HttpStatus.CONFLICT, ExceptionList.USEAT.getHttpStatus());
        assertEquals("USER 'EMAIL' ALREADY EXISTS", ExceptionList.USEAT.getMessage());
    }

    @Test
    @DisplayName("USUNAT should have CONFLICT status")
    void testUsunat() {
        assertEquals(HttpStatus.CONFLICT, ExceptionList.USUNAT.getHttpStatus());
        assertEquals("USER 'USERNAME' ALREADY EXISTS", ExceptionList.USUNAT.getMessage());
    }

    @Test
    @DisplayName("USDFDP should have CONFLICT status")
    void testUsdfdp() {
        assertEquals(HttpStatus.CONFLICT, ExceptionList.USDFDP.getHttpStatus());
        assertEquals("DATA INTEGRITY VIOLATION: DUPLICATE KEY", ExceptionList.USDFDP.getMessage());
    }

    @Test
    @DisplayName("Should have 8 enum values")
    void testEnumCount() {
        assertEquals(8, ExceptionList.values().length);
    }

    @Test
    @DisplayName("Should resolve valueOf correctly")
    void testValueOf() {
        assertEquals(ExceptionList.UNFD, ExceptionList.valueOf("UNFD"));
        assertEquals(ExceptionList.USAA, ExceptionList.valueOf("USAA"));
    }
}
