package masterjunit5.mockito

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


internal class LoginControllerTest {
    lateinit var loginService: LoginService;
    lateinit var loginRepository: LoginRepository;
    var userForm = UserForm("foo", "bar");


    @Test
    fun testLoginOk() {
        loginRepository = mockk<LoginRepository>();
        loginService = LoginServiceImpl(loginRepository);
        every { loginRepository.login(userForm) } returns true;
        assertTrue(loginService.login(userForm));
        verify(atLeast = 1) { loginRepository.login(userForm) }
    }

    @Test
    fun testLoginKo() {
        loginRepository = mockk();
        loginService = LoginServiceImpl(loginRepository);
        every { loginRepository.login(userForm) } returns false;
        assertFalse(loginService.login(userForm))
        verify(atLeast = 1) { loginRepository.login(userForm) }
    }

    @Test
    @Throws(LoginException::class)
    fun `test logining twice`() {
        loginRepository = mockk();
        loginService = LoginServiceImpl(loginRepository);
        every { loginRepository.login(userForm) } returns true;
        assertThrows<LoginException> {
            loginService.login(userForm);
            loginService.login(userForm);
        }
    }
}