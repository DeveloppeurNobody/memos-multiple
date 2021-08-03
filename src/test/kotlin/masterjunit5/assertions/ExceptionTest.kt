package masterjunit5.assertions

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


internal class ExceptionTest {
    @Test
    fun standardAssertions() {
        assertEquals(2, 2);
        assertTrue(true, "The optional assertion message is now the last parameter");
        assertFalse(false) { "Really expensive message ." }
    }
}