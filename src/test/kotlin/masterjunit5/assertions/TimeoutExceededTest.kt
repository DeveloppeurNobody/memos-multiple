package masterjunit5.assertions

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertTimeout
import java.time.Duration

internal class TimeoutExceededTest {
    @Test
    fun testTimeoutNotExceeded() {
        assertTimeout(Duration.ofMinutes(2)) {
            // perform taks that takes less than 2 minutes
        }
    }

    @Disabled
    @Test
    fun testTimeoutExceeded() {
        assertTimeout(Duration.ofMillis(10)) { Thread.sleep(100); }
    }
}