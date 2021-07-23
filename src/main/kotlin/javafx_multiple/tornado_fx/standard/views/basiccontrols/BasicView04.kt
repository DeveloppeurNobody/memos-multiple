package javafx_multiple.tornado_fx.standard.views.basiccontrols

import javafx.scene.control.TextField
import tornadofx.*

class BasicView04 : View() {
    // better than lateinit var instead we use 'singleAssign()' delegation and secure for multithreading
    var firstNameField: TextField by singleAssign()
    var lastNameField: TextField by singleAssign()

    override val root = vbox {
        hbox {
            label("First Name")
            firstNameField = textfield()
        }
        hbox {
            label("Last Name")
            lastNameField = textfield()
        }
        button("LOGIN") {
            useMaxWidth = true
            action {
                println("Logging in as ${firstNameField.text} ${lastNameField.text}")
            }
        }
    }
}