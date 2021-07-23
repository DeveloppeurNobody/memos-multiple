package javafx_multiple.tornado_fx.master_model

import javafx.collections.FXCollections
import tornadofx.Controller

class PersonController : Controller() {
    val persons = FXCollections.observableArrayList<Person>()

    init {
        // run this async
        persons += Person("John", "Smith")
        persons += Person("Jane", "Jones")
    }
}