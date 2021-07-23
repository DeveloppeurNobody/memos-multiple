package javafx_multiple.tornado_fx.todomvc.app

import javafx.application.Application
import tornadofx.App
import javafx_multiple.tornado_fx.todomvc.view.MainView

class TodoMvcAppFX : App(MainView::class, Styles::class) {
//    override fun start(stage: Stage) {
//        with(stage) {
//            width = 500.0
//            height = 500.0
//        }
//        super.start(stage)
//    }
}

fun main(args: Array<String>) {
    Application.launch(TodoMvcAppFX::class.java, *args)
}