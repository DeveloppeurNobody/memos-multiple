package masterjunit5.dynamic_tests

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.stream
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.function.ThrowingConsumer

import java.util.function.Function
import java.util.stream.Stream



internal class StreamExampleTest {
    // because of @Disabled none of test will be executed

    @Disabled
    @TestFactory
    fun `test stream test`(): Stream<DynamicTest> {
        // input data
        var array: Array<Int> = arrayOf(1, 2, 3, 4, 5, 10, 11, 50);
        var inputGenerator: Iterator<Int> = mutableListOf<Int>(*array).iterator()

        // display names
        var displayNameGenerator: Function<Int, String> = Function { input -> "Data Input $input" }

        // test executor ==> only data that % 2 == 0 that gonna passed, other will failed
        var testExecutor: ThrowingConsumer<Int> = ThrowingConsumer{ input -> println(input); assertTrue(input % 2 == 0); }

        // Returns a stream of dynamic tests
        return stream(inputGenerator, displayNameGenerator, testExecutor);
    }



}