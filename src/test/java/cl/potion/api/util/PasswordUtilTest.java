package cl.potion.api.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PasswordUtil Tests")
class PasswordUtilTest {

    @Test
    @DisplayName("Should encrypt a password and return a BCrypt hash")
    void testPasswordEncrypt() {
        String raw = "mySecret123";
        String hashed = PasswordUtil.passwordEncrypt(raw);

        assertNotNull(hashed);
        assertNotEquals(raw, hashed);
        assertTrue(hashed.startsWith("$2a$") || hashed.startsWith("$2b$"));
    }

    @Test
    @DisplayName("Should return true when raw password matches the hash")
    void testPasswordDecryptMatch() {
        String raw = "correctPassword";
        String hashed = PasswordUtil.passwordEncrypt(raw);

        assertTrue(PasswordUtil.passwordDecrypt(raw, hashed));
    }

    @Test
    @DisplayName("Should return false when raw password does not match the hash")
    void testPasswordDecryptNoMatch() {
        String raw = "correctPassword";
        String hashed = PasswordUtil.passwordEncrypt(raw);

        assertFalse(PasswordUtil.passwordDecrypt("wrongPassword", hashed));
    }

    @Test
    @DisplayName("Should produce different hashes for the same input (salted)")
    void testPasswordEncryptProducesDifferentHashes() {
        String raw = "samePassword";
        String hash1 = PasswordUtil.passwordEncrypt(raw);
        String hash2 = PasswordUtil.passwordEncrypt(raw);

        assertNotEquals(hash1, hash2);
        assertTrue(PasswordUtil.passwordDecrypt(raw, hash1));
        assertTrue(PasswordUtil.passwordDecrypt(raw, hash2));
    }
}
