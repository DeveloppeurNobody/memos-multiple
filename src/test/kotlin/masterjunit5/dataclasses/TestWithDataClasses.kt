package masterjunit5.dataclasses

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

internal class TestWithDataClasses {

    @Test
    fun `testing with data classes`() {
        // without data classes
        assertEquals(2, 1);

        // *** output:
        // expected: <2> but was: <1>
        // Expected :2
        // Actual   :1


        // with data classes
        data class NumberEssai(var id: Int, val number: String) { }

        var exp: NumberEssai = NumberEssai(1, "Hello");
        var act = NumberEssai(2, "Hello");

        assertEquals(exp, act);

        // *** output
        // expected: <NumberEssai(id=1, number=Hello)> but was: <NumberEssai(id=2, number=Hello)>
        // Expected :NumberEssai(id=1, number=Hello)
        // Actual   :NumberEssai(id=2, number=Hello)
    }

    ////////////
    // Nested classes
    @DisplayName("DirTestTTTT")
    @Tag("essai")
    @Nested
    class DirTest {
        @Test
        fun `testing with data classes`() {
            // without data classes
            assertEquals(2, 1);
        }

        @Test
        fun `testing with data`() {
            // without data classes
            assertEquals(2, 2);
        }
    }

}