package masterjunit5.dynamic_tests

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.util.stream.Stream

internal class DynamicExampleTest {

    @TestFactory
    fun `test dynamic tests from stream`(): Stream<DynamicTest> {
        var inputStream: Stream<String> = Stream.of("A", "B", "C");
        return inputStream.map { input ->
            DynamicTest.dynamicTest("Display name for input $input") { println("Testing $input")}
        }
    }
}