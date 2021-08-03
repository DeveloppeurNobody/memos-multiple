package masterjunit5.assertions

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals

internal class GroupedAssertionsTest {
    @Test
    fun testGroupedAssertions() {
        var address: Address = Address("John", "Smith");

        // In a grouped assertion all assertions are executed,
        // and any failures will be reported together.
        assertAll("address",
            { assertEquals("John", address.firstName); },
            { assertEquals("Smith", address.lastName); });
    }


}