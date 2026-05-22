package cl.potion.api.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

import cl.potion.api.exception.ExceptionList;

@DisplayName("ExceptionUtil Tests")
class ExceptionUtilTest {

    @Test
    @DisplayName("Should return USUNAT when message contains (username)")
    void testDuplicateConstraintErrorUsername() throws Exception {
        var ex = new DataIntegrityViolationException(
                "ERROR: duplicate key value violates unique constraint \"game_user_username_key\" Detail: Key (username)=(testuser) already exists.");

        ExceptionList result = ExceptionUtil.duplicateConstraintError(ex);

        assertEquals(ExceptionList.USUNAT, result);
    }

    @Test
    @DisplayName("Should return USEAT when message contains (email)")
    void testDuplicateConstraintErrorEmail() throws Exception {
        var ex = new DataIntegrityViolationException(
                "ERROR: duplicate key value violates unique constraint \"game_user_email_key\" Detail: Key (email)=(test@test.com) already exists.");

        ExceptionList result = ExceptionUtil.duplicateConstraintError(ex);

        assertEquals(ExceptionList.USEAT, result);
    }

    @Test
    @DisplayName("Should return USDFDP when message contains neither (username) nor (email)")
    void testDuplicateConstraintErrorDefault() throws Exception {
        var ex = new DataIntegrityViolationException("some other integrity violation");

        ExceptionList result = ExceptionUtil.duplicateConstraintError(ex);

        assertEquals(ExceptionList.USDFDP, result);
    }
}
