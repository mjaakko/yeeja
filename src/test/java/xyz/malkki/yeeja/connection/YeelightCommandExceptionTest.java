package xyz.malkki.yeeja.connection;

import org.junit.Test;
import static org.junit.Assert.*;

public class YeelightCommandExceptionTest {
    @Test
    public void testGetCode() {
        YeelightCommandException exception = new YeelightCommandException(1, "test");

        assertEquals(1, exception.getCode());
    }

    @Test
    public void testToString() {
        YeelightCommandException exception = new YeelightCommandException(1, "test");

        assertEquals("test (1)", exception.toString());
    }

    @Test
    public void testHashCodeSame() {
        YeelightCommandException exception1 = new YeelightCommandException(1, "test");
        YeelightCommandException exception2 = new YeelightCommandException(1, "test");

        assertEquals(exception1.hashCode(), exception2.hashCode());
    }

    @Test
    public void testHashCodeNotSame() {
        YeelightCommandException exception1 = new YeelightCommandException(2, "test");
        YeelightCommandException exception2 = new YeelightCommandException(1, "test");

        assertNotEquals(exception1.hashCode(), exception2.hashCode());
    }

    @Test
    public void testEqualsWhenSameObject() {
        YeelightCommandException exception1 = new YeelightCommandException(1, "test");

        assertTrue(exception1.equals(exception1));
    }

    @Test
    public void testNotEqualsWithNull() {
        YeelightCommandException exception1 = new YeelightCommandException(1, "test");

        assertFalse(exception1.equals(null));
    }

    @Test
    public void testNotEqualsWithDifferentType() {
        YeelightCommandException exception1 = new YeelightCommandException(1, "test");

        assertFalse(exception1.equals("test"));
    }

    @Test
    public void testEqualsWithSameCodeAndMessage() {
        YeelightCommandException exception1 = new YeelightCommandException(1, "test");
        YeelightCommandException exception2 = new YeelightCommandException(1, "test");

        assertTrue(exception1.equals(exception2));
    }

    @Test
    public void testNotEqualsWithDifferentCode() {
        YeelightCommandException exception1 = new YeelightCommandException(1, "test");
        YeelightCommandException exception2 = new YeelightCommandException(2, "test");

        assertFalse(exception1.equals(exception2));
    }

    @Test
    public void testNotEqualsWithDifferentMessage() {
        YeelightCommandException exception1 = new YeelightCommandException(1, "test");
        YeelightCommandException exception2 = new YeelightCommandException(1, "_test");

        assertFalse(exception1.equals(exception2));
    }
}
