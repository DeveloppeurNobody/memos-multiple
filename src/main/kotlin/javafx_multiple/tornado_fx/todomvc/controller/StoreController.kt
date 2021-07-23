package javafx_multiple.tornado_fx.todomvc.controller

import javafx_multiple.tornado_fx.todomvc.model.FilterState
import javafx_multiple.tornado_fx.todomvc.model.TodoItem
import tornadofx.Controller
import tornadofx.SortedFilteredList
import tornadofx.invalidate

class StoreController : Controller() {
    val todos = SortedFilteredList<TodoItem>()

    fun addTodo(text: String = "empty") = todos.add(TodoItem(text))

    fun remoteTodo(todoItem: TodoItem) = todos.remove(todoItem)

    fun toggleCompleted(completed: Boolean = false) = with (todos) {
        filter { it.completed != completed}
            .forEach { it.completed = completed }

        invalidate();
    }

    fun filterBy(state: FilterState) = when(state) {
        FilterState.Completed -> todos.predicate = { it.completed }
        FilterState.Active -> todos.predicate = { !it.completed }
        else -> todos.predicate = { true }
    }
}