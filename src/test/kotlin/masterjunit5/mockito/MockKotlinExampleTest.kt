package masterjunit5.mockito

import io.mockk.*
import org.junit.jupiter.api.Test
import java.lang.Exception
import java.net.URL
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalTime
import java.util.*


class MockKotlinExampleTest {

    // for function: verify(exactly = 0) { adder.addTwo(any()) }
    // for mock    : verify { mock wasNot Called } is used not for function but for whole mock



    // Regular Mocks
    @Test
    fun `regular mock`() {
        val clock = mockk<MyClock>()
        every { clock.currentTime() } returns "7:20";
        println(clock.currentTime());
        verify { clock.currentTime() }
    }

    @Test
    fun `regular spy`() {
        val clock = spyk<MyClock>()
        println(clock.currentTime());
        verify { clock.currentTime() }
    }

    // Mocks with behaviour
    @Test
    fun `mock with complex behaviour`() {
        val clock = mockk<MyClock>();
        every { clock.currentTime() } answers { dateFormat.format(Date()) }
        println(clock.currentTime().toString());
        verify { clock.currentTime() }
    }

    companion object {
        val dateFormat = SimpleDateFormat("HH:mm");
    }

    // Mocks chained calls
    @Test
    fun mockingChainedCalls() {
        val oven = mockk<Oven>();
        every { oven.clock.currentTime() } returns "8:20";
        println(oven.clock.currentTime())
        verify { oven.clock.currentTime() }
    }

    // Mockks hierarchies
    @Test
    fun `hierarchical mocking`() {
        val oven = mockk<Oven>();
        every { oven.clock } returns mockk {
            every { currentTime() } returns "11:20";
        }

        println("--- [ hierarchical mocking ] ${oven.clock.currentTime()}");
        println("--- [ hierarchical mocking ] ${oven.clock.currentTime()}");
        println("--- [ hierarchical mocking ] ${oven.clock.currentTime()}");
    }

    // Mockk Objects
    @Test
    fun `object mocking`() {
        var urlHelper = UrlHelper();
        mockkObject(urlHelper)
        every { urlHelper.baseUrl } returns URL("http://mockUrl.com").toString()

        println(urlHelper.baseUrl);
        urlHelper.baseUrl = "http://localhost";
        println(urlHelper.baseUrl);
    }

    // Mock Unit
    @Test
    fun `mocking functions that return Unit`() {
        val clock = mockk<MyClock>();
        every { clock.changesBatteries() } just Runs

        clock.changesBatteries();
        verify { clock.changesBatteries() }
    }

    // Mock Nothing
    @Test
    fun `mocking functions that return Nothing`() {
        val clock = mockk<MyClock>();
        every { clock.runForever() } throws Exception("called runForerver");
        try {
            clock.runForever()
        } catch (e: Exception) {
            System.err.println("--- [exception called] e: $e")
        }
       //assertThrows<Exception> { clock.runForever() }
    }

    // Mock extensions functions
    @Test
    fun `mocking functions extension functions`() {
        with(mockk<MyClock>()) {
            every { Duration.ofMinutes(5).startTimer() } returns true;
            println("Checking if true since in extension function we have false: ${Duration.ofMinutes(5).startTimer()}");
        }


    }

    // Mock top level functions
//    @Test
//    fun `mocking top level functions`() {
//        mockkStatic("mockk.ModelsKt")
//        every { resolve(any()) } returns URL("http://mockk.com/users/1")
//
//        //...
//    }

}

private fun Duration.startTimer(): Boolean {
    return false;
}


class MyClock {
    fun currentTime(): String {
        var value = LocalTime.now()
        return value.toString();
    }

    fun fakeCall(): String {
        return "nothing";
    }

    fun changesBatteries(): Unit {
        for (i in 0 until 5) {
            println("Charging batterie status: ${2*i}%");
        }
    }

    fun runForever() {
        var i = 0;
        while (i < 100) {
            println("Hi");
            i++;
        }
    }
}

class Oven(var clock: MyClock = MyClock()) {
}

data class UrlHelper(var baseUrl: String = "") {
}


