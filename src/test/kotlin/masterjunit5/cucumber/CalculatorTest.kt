package masterjunit5.cucumber

//
//import io.cucumber.java.en.Given
//import io.cucumber.java.en.Then
//import io.cucumber.java.en.When
//import org.junit.jupiter.api.Assertions
//
//
//internal class CalculatorSteps {
//    private var calc: Calculator? = null
//
//    @Given("^a calculator I just turned on$")
//    fun setup() {
//        calc = Calculator()
//    }
//
//    @When("^I add (\\d+) and (\\d+)$")
//    fun add(arg1: Int, arg2: Int) {
//        calc!!.push(arg1)
//        calc!!.push(arg2)
//        calc!!.push("+")
//    }
//
//    @When("^I substract (\\d+) to (\\d+)$")
//    fun substract(arg1: Int, arg2: Int) {
//        calc!!.push(arg1)
//        calc!!.push(arg2)
//        calc!!.push("-")
//    }
//
//    @Then("^the result is (\\d+)$")
//    fun the_result_is(expected: Double) {
//        Assertions.assertEquals(expected, calc!!.value())
//    }
//}
