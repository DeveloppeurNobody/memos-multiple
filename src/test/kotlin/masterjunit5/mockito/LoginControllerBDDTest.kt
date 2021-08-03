package masterjunit5.mockito

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class LoginControllerBDDTest {
    @MockK
    lateinit var loginService: LoginService;

    @InjectMockKs
    lateinit var loginController: LoginControllerImpl;

    var userForm = UserForm("foo", "bar");

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this);

    @Test
    fun testLoginOk() {
        every { loginService.login(userForm) } returns true;
        assertEquals("OK", loginController.login(userForm));
    }
}