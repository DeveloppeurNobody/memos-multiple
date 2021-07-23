package javafx_multiple.tornado_fx.loginapp_github.view

import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Orientation
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import javafx_multiple.tornado_fx.loginapp_github.controller.LoginController
import tornadofx.*;

class LoginScreen : View("Login") {
    val model = ViewModel();
    val username = model.bind { SimpleStringProperty() }
    val password = model.bind { SimpleStringProperty() }
    val loginController: LoginController by inject()

    override val root = form {
        fieldset(labelPosition = Orientation.VERTICAL) {
            field("username") {
                textfield(username).required()
            }
            field("password") {
                passwordfield(password).required()
            }
            button("Log in") {
                enableWhen(model.valid)
                isDefaultButton = true
                useMaxWidth = true
                action {
                    runAsyncWithProgress { loginController.login(username.value, password.value) }
                }
            }
        }
        label(loginController.statusProperty) {
            style {
                paddingTop = 10
                textFill = Color.RED
                fontWeight = FontWeight.BOLD
            }
        }
    }

    override fun onDock() {
        username.value = ""
        password.value = ""
        model.clearDecorators()
    }
}