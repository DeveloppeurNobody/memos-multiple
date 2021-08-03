package masterjunit5.dependency_injection

import org.junit.jupiter.api.*

internal class TestInfoTest {
    @BeforeEach
    fun setUp(testInfo: TestInfo) {
        var displayName: String = testInfo.displayName;
        System.out.printf("@BeforeEach %s %n", displayName);
    }

    @Test
    @DisplayName("My Test")
    @Tag("my-tag")
    fun `test testOne`(testInfo: TestInfo) {
        // this => testInfo
        with(testInfo) {
            println(displayName);
            println(tags);
            println(testClass);
            println(testMethod);
        }
    }

    @Test
    fun `test testTwo`() {

    }
}