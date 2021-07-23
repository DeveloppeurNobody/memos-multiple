package javafx_multiple.tornado_fx.master_model

import tornadofx.App
import tornadofx.View
import tornadofx.hbox
import tornadofx.launch


class MainView : View("Hello TornadoFX Application") {
    override val root = hbox {
        this += PersonList::class
        this += PersonEditor::class
    }
}

class MainApp : App(MainView::class)

fun main(args: Array<String>) {
    launch<MainApp>(*args)
}