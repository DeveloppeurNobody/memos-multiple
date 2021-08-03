package masterjunit5.dynamic_tests

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals

internal class CollectionDynamicTest {

    // warning: this test will raise an exception
    @Disabled
    @TestFactory
    fun dynamicTestWithInvalidReturnType(): MutableList<String> {
        return mutableListOf("Hello");
    }

    @TestFactory
    fun `test dynamic tests from collection`(): MutableList<DynamicTest> {
        return mutableListOf(
            DynamicTest.dynamicTest("1st dynamic test") { assertTrue(true) },
            DynamicTest.dynamicTest("2nd dynamic test") { assertEquals(4, 2 * 2)}
        );
    }

    @TestFactory
    fun `test dynamic tests from iterable`(): Iterable<DynamicTest> {
        return mutableListOf(
            DynamicTest.dynamicTest("3rd dynamic test") { assertTrue(true) },
            DynamicTest.dynamicTest("4th dynamic test") { assertEquals(4, 2 * 2)}
        );
    }

    @TestFactory
    fun `test dynamic tests from iterator`(): Iterator<DynamicTest> {
        return mutableListOf<DynamicTest>(
            DynamicTest.dynamicTest("5th dynamic test") { assertTrue(true)},
            DynamicTest.dynamicTest("6th dynamic test") { assertEquals(4, 2 * 2)}
        ).iterator();
    }
}