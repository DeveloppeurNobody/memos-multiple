package masterjunit5.dependency_injection

import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.RepetitionInfo

internal class RepetitionIntoTest {
    @RepeatedTest(2)
    fun test(repetitionInfo: RepetitionInfo) {
        println("** Test ${repetitionInfo.currentRepetition} / ${repetitionInfo.totalRepetitions}");
    }
}