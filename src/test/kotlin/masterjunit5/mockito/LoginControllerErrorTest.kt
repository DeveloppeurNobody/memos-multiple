package masterjunit5.mockito

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

internal class LoginControllerErrorTest {
    // @InjectMocks
    lateinit var loginController: LoginController;
    // @Mock
    lateinit var loginService: LoginService;
    // dummy user
    var userForm = UserForm("foo", "bar");

    @BeforeEach
    fun setUp() {
        loginService = mockk<LoginService>();
        loginController = LoginControllerImpl(loginService);
    }

    @Test
    @Throws(LoginException::class)
    fun testLoginException() {
        // Expectations
        every { loginService.login(userForm) } throws IllegalArgumentException();
        // Evercice
        var response: String = loginController.login(userForm);
        // Verify
        assertEquals("Error", response);
    }
}