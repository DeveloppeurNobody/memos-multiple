package javafx_multiple.tornado_fx.todomvc.view

import javafx_multiple.tornado_fx.todomvc.app.Styles
import javafx_multiple.tornado_fx.todomvc.controller.StoreController
import tornadofx.*

class Header : View() {
    val storeController: StoreController by inject()
    val allDone = booleanBinding(storeController.todos.items) { all { it.completed } }

    override val root = vbox {
        addClass(Styles.header)
        label("todos").setId(Styles.title)
        hbox {
            addClass(Styles.addItemRoot)
            checkbox {
                addClass(Styles.mainCheckBox)
                visibleWhen { booleanBinding(storeController.todos) { isNotEmpty() } }
                action { storeController.toggleCompleted(isSelected) }
                allDone.onChange { isSelected = it }
            }
            textfield {
                promptText = "What needs to be done ?"
                action {
                    storeController.addTodo(text)
                    clear()
                }
            }
        }
    }
}
