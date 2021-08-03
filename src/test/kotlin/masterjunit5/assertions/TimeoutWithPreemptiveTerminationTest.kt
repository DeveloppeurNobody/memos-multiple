package masterjunit5.assertions

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertTimeoutPreemptively
import java.time.Duration

internal class TimeoutWithPreemptiveTerminationTest {
    @Disabled
    @Test
    fun testTimeoutExceededWithPreemptiveTermination() {
        assertTimeoutPreemptively(Duration.ofMillis(10)) { Thread.sleep(100); }
    }
}