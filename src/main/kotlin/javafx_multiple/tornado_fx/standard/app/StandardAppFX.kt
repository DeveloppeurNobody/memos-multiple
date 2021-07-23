package javafx_multiple.tornado_fx.standard.app

import javafx.application.Application
import javafx.stage.Stage
import javafx_multiple.tornado_fx.standard.views.basiccontrols.BasicView04
import tornadofx.App

class StandardAppFX : App(BasicView04::class) {
    override fun start(stage: Stage) {
        with(stage) {
            width = 500.0
            height = 500.0
        }
        super.start(stage)
    }
}

fun main(args: Array<String>) {
    Application.launch(StandardAppFX::class.java, *args)
}