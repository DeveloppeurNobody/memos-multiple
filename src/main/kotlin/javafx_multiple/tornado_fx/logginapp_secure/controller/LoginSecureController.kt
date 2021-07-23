package javafx_multiple.tornado_fx.logginapp_secure.controller

import javafx_multiple.tornado_fx.logginapp_secure.view.LoginScreen
import javafx_multiple.tornado_fx.logginapp_secure.view.SecureScreen
import tornadofx.Controller
import tornadofx.runLater

class LoginSecureController : Controller() {
    val loginScreen: LoginScreen by inject()
    val secureScreen: SecureScreen by inject()

    companion object {
        const val USERNAME = "username"
        const val PASSWORD = "password"
    }

    fun init() {
        with(config) {
            if (containsKey(USERNAME) && containsKey(PASSWORD))
                tryLogin(string(USERNAME)?: "empty", string(PASSWORD)?: "empty", true)
            else
                showLoginScreen("Please Log in")
        }
    }

    fun showLoginScreen(message: String, shake: Boolean = false) {
        secureScreen.replaceWith(loginScreen, sizeToScene = true, centerOnScreen = true)
        runLater {
            if (shake) loginScreen.shakeStage()
        }
    }

    fun showSecureScreen() {
        loginScreen.replaceWith(secureScreen, sizeToScene = true, centerOnScreen = true)
    }

    fun tryLogin(username: String, password: String, remember: Boolean) {
        runAsync {
            username == "admin" && password == "secret"
        } ui { successfulllogin ->
            if (successfulllogin) {
                loginScreen.clear()

                if (remember) {
                    with(config) {
                        set(USERNAME to username)
                        set(PASSWORD to password)
                        save()
                    }
                }

                showSecureScreen();
            } else { showLoginScreen("Login failed. Please try again.", true) }
        }
    }

    fun logout() {
        with(config) {
            remove(USERNAME)
            remove(PASSWORD)
            save()
        }

        showLoginScreen("Log in as another user");
    }
}