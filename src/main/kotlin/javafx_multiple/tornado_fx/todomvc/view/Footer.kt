package javafx_multiple.tornado_fx.todomvc.view

import javafx_multiple.tornado_fx.todomvc.app.Styles
import javafx_multiple.tornado_fx.todomvc.controller.StoreController
import javafx_multiple.tornado_fx.todomvc.model.FilterState
import tornadofx.*

class Footer : View() {
    val storeController: StoreController by inject();
    val itemsLeft = integerBinding(storeController.todos.items) { count { !it.completed } }

    override val root = hbox {
        addClass(Styles.footer)
        label(stringBinding(itemsLeft) { "$value item${value.plural} left" } )
        hbox {
            togglegroup {
                for (state in FilterState.values())
                    togglebutton(state.name).whenSelected { storeController.filterBy(state) }
            }
        }
    }

    val Int.plural: String get() = if (this == 1) "" else "s"
}
