package javafx_multiple.tornado_fx.loginapp_github.controller

import javafx.beans.property.SimpleStringProperty
import javafx_multiple.tornado_fx.loginapp_github.model.UserModel
import javafx_multiple.tornado_fx.loginapp_github.view.LoginScreen
import javafx_multiple.tornado_fx.loginapp_github.view.WelcomScreen
import tornadofx.*

class LoginController : Controller() {
    val statusProperty = SimpleStringProperty("");
    var status by statusProperty

    val api: Rest by inject()
    val user: UserModel by inject()

    init {
        api.baseURI = "https://api.github.com/"
    }

    fun login(username: String = "", password: String = "") {
        runLater { status = "" }
        api.setBasicAuth(username, password)
        val response = api.get("user")
        val json = response.one()
        runLater {
            if (response.ok()) {
                user.item = json.toModel()
                find(LoginScreen::class).replaceWith(WelcomScreen::class, sizeToScene = true, centerOnScreen = true)
            } else {
                status = json.string("message") ?: "Login failed"
            }
        }
    }

    fun logout() {
        user.item = null;
        primaryStage
            .uiComponent<UIComponent>()
            ?.replaceWith(LoginScreen::class, sizeToScene = true, centerOnScreen = true)

    }
}