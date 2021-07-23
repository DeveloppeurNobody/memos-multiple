package javafx_multiple.tornado_fx.todomvc.view

import javafx.scene.layout.Priority
import javafx_multiple.tornado_fx.todomvc.app.Styles
import javafx_multiple.tornado_fx.todomvc.controller.StoreController
import javafx_multiple.tornado_fx.todomvc.model.TodoItem
import javafx_multiple.tornado_fx.todomvc.model.TodoItemModel
import tornadofx.*

class TodoItemFragment : ListCellFragment<TodoItem>() {
    val storeController: StoreController by inject()
    val todo = TodoItemModel(itemProperty)

    override val root = hbox {
        addClass(Styles.itemRoot)
        checkbox(property = todo.completed) {
            action {
                startEdit()
                commitEdit(item)
            }
        }
        label(todo.text) {
            setId(Styles.contentLabel)
            hgrow = Priority.ALWAYS
            useMaxSize = true
            removeWhen { editingProperty }
            toggleClass(Styles.strikethrough, todo.completed)
        }
        textfield(todo.text) {
            hgrow = Priority.ALWAYS
            removeWhen { editingProperty.not() }
            whenVisible { requestFocus() }
            action { commitEdit(item) }
        }
        button(graphic = Styles.closeIcon()) {
            removeWhen { parent.hoverProperty().not().or(editingProperty) }
            action { storeController.remoteTodo(item) }
        }
    }
}