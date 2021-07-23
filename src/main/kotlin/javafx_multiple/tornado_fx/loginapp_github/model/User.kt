package javafx_multiple.tornado_fx.loginapp_github.model

import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import javax.json.JsonObject

class User : JsonModel {
    val nameProperty = SimpleStringProperty()
    var name by nameProperty

    override fun updateModel(json: JsonObject) {
        with(json) {
            name = string("name")
        }
    }
}

class UserModel : ItemViewModel<User>() {
    val name = bind(User::nameProperty)
}