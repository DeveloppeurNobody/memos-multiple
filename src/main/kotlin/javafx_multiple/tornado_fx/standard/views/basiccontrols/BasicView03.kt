package javafx_multiple.tornado_fx.standard.views.basiccontrols

import tornadofx.*;

class BasicView03 : View() {

    override val root = vbox {
        hbox {
            label("First Name")
            textfield()
        }
        hbox {
            label("Last Name")
            textfield()
        }
        button("LOGIN") {
            useMaxWidth = true
        }
    }
}