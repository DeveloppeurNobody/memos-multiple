package javafx_multiple.tornado_fx.loginapp_github.app

import javafx_multiple.tornado_fx.loginapp_github.view.LoginScreen
import tornadofx.App
import tornadofx.launch

class LoginApp : App(LoginScreen::class) {
}

fun main(args: Array<String>) {
    launch<LoginApp>(*args)
}