package cl.potion.api.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PotionEntity Tests")
class PotionEntityTest {

    private PotionEntity potionEntity;
    private Date now;

    @BeforeEach
    void setUp() {
        now = new Date();
        potionEntity = new PotionEntity();
    }

    @Test
    @DisplayName("Should create PotionEntity with default constructor")
    void testDefaultConstructor() {
        assertNotNull(potionEntity);
        assertNull(potionEntity.getId());
        assertNull(potionEntity.getName());
        assertNull(potionEntity.getType());
        assertNull(potionEntity.getCreateAt());
        assertNull(potionEntity.getUpdatedAt());
    }

    @Test
    @DisplayName("Should create PotionEntity with all arguments constructor")
    void testFullConstructor() {
        BigInteger id = BigInteger.valueOf(1L);
        PotionEntity potion = new PotionEntity(id, "Health Potion", "healing", now, now);

        assertEquals(id, potion.getId());
        assertEquals("Health Potion", potion.getName());
        assertEquals("healing", potion.getType());
        assertEquals(now, potion.getCreateAt());
        assertEquals(now, potion.getUpdatedAt());
    }

    @Test
    @DisplayName("Should create PotionEntity using Builder pattern")
    void testBuilder() {
        BigInteger id = BigInteger.valueOf(2L);
        PotionEntity potion = PotionEntity.builder()
                .id(id)
                .name("Mana Potion")
                .type("magic")
                .createAt(now)
                .updatedAt(now)
                .build();

        assertEquals(id, potion.getId());
        assertEquals("Mana Potion", potion.getName());
        assertEquals("magic", potion.getType());
        assertEquals(now, potion.getCreateAt());
        assertEquals(now, potion.getUpdatedAt());
    }

    @Test
    @DisplayName("Should set and get name")
    void testSetAndGetName() {
        potionEntity.setName("Fire Potion");
        assertEquals("Fire Potion", potionEntity.getName());
    }

    @Test
    @DisplayName("Should set and get type")
    void testSetAndGetType() {
        potionEntity.setType("fire");
        assertEquals("fire", potionEntity.getType());
    }

    @Test
    @DisplayName("Should set and get id")
    void testSetAndGetId() {
        BigInteger id = BigInteger.valueOf(42L);
        potionEntity.setId(id);
        assertEquals(id, potionEntity.getId());
    }

    @Test
    @DisplayName("Should set and get createAt")
    void testSetAndGetCreateAt() {
        Date createDate = new Date();
        potionEntity.setCreateAt(createDate);
        assertEquals(createDate, potionEntity.getCreateAt());
    }

    @Test
    @DisplayName("Should set and get updatedAt")
    void testSetAndGetUpdatedAt() {
        Date updateDate = new Date();
        potionEntity.setUpdatedAt(updateDate);
        assertEquals(updateDate, potionEntity.getUpdatedAt());
    }

    @Test
    @DisplayName("Should generate toString representation")
    void testToString() {
        potionEntity.setName("Invisibility Potion");
        potionEntity.setType("stealth");

        String toString = potionEntity.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Invisibility Potion") || toString.contains("PotionEntity"));
    }
}
