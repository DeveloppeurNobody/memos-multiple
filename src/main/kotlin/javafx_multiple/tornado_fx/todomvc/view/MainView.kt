package javafx_multiple.tornado_fx.todomvc.view

import tornadofx.*

class MainView : View("My View") {
    override val root = borderpane {
        top(Header::class)
        center(TodoList::class)
        bottom(Footer::class)
    }
}
