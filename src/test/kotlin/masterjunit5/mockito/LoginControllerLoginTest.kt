package masterjunit5.mockito

import io.mockk.called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


internal class LoginControllerLoginTest {
    // Inject Mocks
    lateinit var loginController: LoginController;

    // Mocks
    lateinit var loginService: LoginService;

    var userForm = UserForm("foo", "bar")

    @BeforeEach
    fun setUp() {
        loginService = mockk<LoginService>();
        loginController = LoginControllerImpl(loginService);
    }

    @Test
    fun testLoginOk() {
        // settings expectations (stubbing methods)
        every { loginService.login(userForm) } returns true;

        // excercie SUT
        var responseLogin: String = loginController.login(userForm);

        // verification
        assertEquals("OK", responseLogin);
        verify { loginService.login(userForm) }
        verify(exactly = 1) { loginService.login(any()) }
        //verify { loginService.login(any()) wasNot called }
    }

    @Test
    fun testLoginKo() {
        // settings expectations
        every { loginService.login(userForm) } returns false;

        // Exercice SUT
        var responseLogin: String = loginController.login(userForm);

        // Verification
        assertEquals("KO", responseLogin);
        verify { loginService.login(userForm) };
        verify(exactly = 1) { loginService.login(any()) }
    }
}