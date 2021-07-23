package javafx_multiple.tornado_fx.todomvc.view

import javafx_multiple.tornado_fx.todomvc.controller.StoreController
import tornadofx.*

class TodoList : View("My View") {
    val storeController: StoreController by inject()

    override val root = listview(storeController.todos) {
        isEditable = true
        cellFragment(TodoItemFragment::class)
    }

}
