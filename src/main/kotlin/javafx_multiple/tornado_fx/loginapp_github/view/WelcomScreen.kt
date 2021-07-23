package javafx_multiple.tornado_fx.loginapp_github.view

import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import javafx_multiple.tornado_fx.loginapp_github.controller.LoginController
import javafx_multiple.tornado_fx.loginapp_github.model.UserModel
import tornadofx.*

class WelcomScreen : View("Welcome") {
    val user: UserModel by inject()
    val loginController: LoginController by inject()

    override val root = vbox(10) {
        setPrefSize(800.0, 600.0)
        alignment = Pos.CENTER

        label(user.name) {
            style {
                fontWeight = FontWeight.BOLD
                fontSize = 24.px
            }
        }

        button("Logout").action(loginController::logout)
    }
}