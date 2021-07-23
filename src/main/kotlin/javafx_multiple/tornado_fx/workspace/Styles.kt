package javafx_multiple.tornado_fx.workspace;

import javafx.scene.paint.Color
import tornadofx.Stylesheet
import tornadofx.cssclass
import tornadofx.px


class Styles : Stylesheet() {
    companion object {
        val wrapper by cssclass()
        val consola by cssclass()
    }

    init {
        root {
            prefHeight = 600.px
            prefWidth = 800.px
        }
        textArea and consola {
            baseColor = Color.BLACK
            fontFamily = "Consolas"
            textFill = Color.LIGHTGRAY
        }
    }
}