package masterjunit5.mockito

import java.lang.Exception


fun main() {
    var lo = Loggg();
    println(lo.checkForm(null));
    println(lo.checkForm(""));
    println("Hi")
}

class Loggg {
    @Throws(Exception::class)
    fun checkForm(userForm: String?) {
        require(userForm != null)
//        assert(userForm.username != null);
//        assert(userForm.password != null);
    }
}

class LoginControllerImpl(var loginService: LoginService = LoginServiceImpl()) : LoginController {

    override fun login(userForm: UserForm): String {
        println("LoginController.login $userForm");

        return try {
            when {
                userForm == null -> {
                    "ERROR";
                }
                loginService.login(userForm) -> {
                    "OK";
                }
                else -> {
                    "KO";
                }
            }
        } catch (e: Exception) {
            "Error";
        }
    }

    override fun logout(userForm: UserForm) {
        println("LoginController.logout $userForm");

        loginService.logout(userForm);
    }
}