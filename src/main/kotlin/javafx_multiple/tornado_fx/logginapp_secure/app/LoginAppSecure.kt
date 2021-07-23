
import javafx.stage.Stage
import javafx_multiple.tornado_fx.logginapp_secure.app.Styles
import javafx_multiple.tornado_fx.logginapp_secure.controller.LoginSecureController
import javafx_multiple.tornado_fx.logginapp_secure.view.LoginScreen
import tornadofx.App
import tornadofx.launch

class LoginAppSecure : App(LoginScreen::class, Styles::class) {
    val loginSecureController: LoginSecureController by inject()

    override fun start(stage: Stage) {
        super.start(stage)
        loginSecureController.init();
    }
}

fun main(args: Array<String>) {
    launch<LoginAppSecure>(*args)
}
