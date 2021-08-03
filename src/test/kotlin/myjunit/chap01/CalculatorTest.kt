package myjunit.chap01

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.*


internal class CalculatorTest {
    @Test
    fun add() {
        var calculator: Calculator = Calculator();
        var result: Double = calculator.add(10.toDouble(), 50.toDouble());
        assertEquals(60.toDouble(), result);
    }


    // Implicit conversion String -> Double
    @ParameterizedTest
    @CsvSource(
        "2, 1, 1",  //expected, valueOne, valueTwo
        "3, 2, 1",  //expected, valueOne, valueTwo
        "4, 3, 1",
        "5, 3, 2",
        "6, 3, 3"
    )
    fun parametersTestSum(expected: Double, valueOne: Double, valueTwo: Double) {
//        var expectedAsDouble = expected.toDouble();
//        var valueOneAsDouble = valueOne.toDouble();
//        var valueTwoAsDouble = valueTwo.toDouble();

        var calculator = Calculator();
        assertEquals(expected, calculator.add(valueOne, valueTwo));
    }



//    @ParameterizedTest
//    @ValueSource(ints = [1, 3, 5, -3, 15, Int.MAX_VALUE])
//    fun isOdd_ShouldReturnTrueForOddNumbers(number: Int) {
//        assertTrue((number % 2) == 0);
//    }

}