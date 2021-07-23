package javafx_multiple.tornado_fx.standard.views.basiccontrols

import tornadofx.*
import javafx.scene.control.Button
import javafx.scene.layout.VBox

class BasicView01 : View() {

    override val root = VBox()

    init {
        with(root) {
            this += Button("Press Me")
        }
    }
}