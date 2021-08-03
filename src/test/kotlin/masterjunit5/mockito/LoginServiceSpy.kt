package masterjunit5.mockito

import io.mockk.spyk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class LoginServiceSpy {
    lateinit var loginRepository: LoginRepository;
    lateinit var loginService: LoginService;
    var userOk = UserForm("user1", "p1")
    var userKo = UserForm("foo", "bar")


    @BeforeEach
    fun setUp() {
        loginRepository = spyk<LoginRepositoryImpl>();
        loginService = LoginServiceImpl(loginRepository);
    }

    @Test
    fun `test login Ok`() {
        assertTrue(loginService.login(userOk));
    }

    @Test
    fun `test login Ko`() {
        assertFalse(loginService.login(userKo));
    }


}