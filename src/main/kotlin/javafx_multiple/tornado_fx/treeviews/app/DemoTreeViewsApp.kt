package javafx_multiple.tornado_fx.treeviews.app

import javafx.application.Application
import javafx_multiple.tornado_fx.treeviews.view.MainTreeView
import tornadofx.App
import tornadofx.importStylesheet

class DemoTreeViewsApp : App() {
    override val primaryView = MainTreeView::class

    init {
        importStylesheet(Styles::class)
    }
}

fun main(args: Array<String>) {
    Application.launch(DemoTreeViewsApp::class.java, *args)
}