package masterjunit5.assertions

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class StandardAssertionsTest {

    @Test
    fun testStandardAssertions() {
        assertEquals(2, 2);
        assertTrue(true,
        "The optional assertion message is now the last parameter");
        assertFalse(false, "Really expensive message ");
    }
}