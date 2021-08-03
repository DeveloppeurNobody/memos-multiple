package masterjunit5.assertions

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertTimeout
import java.time.Duration
import kotlin.test.assertEquals

internal class TimeoutWithResultOrMethodTest {
    @Test
    fun testTimeoutNotExceededWithResult() {
        var actualResult: String =
            assertTimeout(Duration.ofMinutes(2)) { return@assertTimeout "hi there"; }
        assertEquals("hi there", actualResult);
    }

    @Test
    fun testTimeoutExceededWithMethod() {
        var actualGreeting = assertTimeout(Duration.ofMinutes(1), TimeoutWithResultOrMethodTest.Companion::greeting)
    }

    companion object {
        fun greeting(): String {
            return "hello world!";
        }
    }
}