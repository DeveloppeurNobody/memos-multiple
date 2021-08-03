package masterjunit5.dataclasses

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class TestAssertExceptions {
    @Test
    fun testExpectedException() {
        Assertions.assertThrows(NumberFormatException::class.java) { "One".toInt() };
    }

    @Test
    fun testExpectedExceptionWithSuperType() {
        Assertions.assertThrows(IllegalArgumentException::class.java) { "One".toInt() }
    }

    @Test
    fun testExpectedExceptionFail() {
        Assertions.assertThrows(IllegalArgumentException::class.java) { "1".toInt() };
    }

}