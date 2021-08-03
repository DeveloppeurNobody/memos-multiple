package masterjunit5.dependency_injection

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestReporter

internal class TestReporterTest {
    @Test
    fun `test report single value`(testReporter: TestReporter) {
        testReporter.publishEntry("key", "value");
    }

    @Test
    fun `test report several values`(testReporter: TestReporter) {
        var values: MutableMap<String, String> = mutableMapOf();
        values["name"] = "john";
        values["surname"] = "doe";

        testReporter.publishEntry(values);
    }
}