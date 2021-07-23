package javafx_multiple.tornado_fx.standard.views.basiccontrols

import javafx.scene.paint.Color
import tornadofx.*

class BasicView02 : View() {

    override val root = vbox {
        button("Press Me") {
            textFill = Color.RED
            action { println("Button pressed!") }
        }
    }
}