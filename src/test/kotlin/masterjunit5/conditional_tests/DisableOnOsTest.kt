package masterjunit5.conditional_tests

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledOnOs
import org.junit.jupiter.api.condition.OS

internal class DisableOnOsTest {

    @DisabledOnOs(OS.LINUX)
    @Test
    fun notLinuxTest() {
        println("Disabled on Linux")
    }

    @DisabledOnOs(OS.WINDOWS)
    @Test
    fun notWinTest() {
        println("Disabled on Windows")
    }

    @DisabledOnOs(OS.MAC)
    @Test
    fun notMacTest() {
        println("Disabled on Mac")
    }
}