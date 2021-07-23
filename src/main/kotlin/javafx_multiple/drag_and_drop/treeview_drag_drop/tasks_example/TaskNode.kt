package javafx_multiple.drag_and_drop.treeview_drag_drop.tasks_example

import java.io.Serializable
import java.util.*


class TaskNode(val name: String) : Serializable {

    override fun hashCode(): Int {
        return Objects.hash(name)
    }

    override fun equals(obj: Any?): Boolean {
        if (obj == null) return false
        if (javaClass != obj.javaClass) return false
        val other = obj as TaskNode
        return name == other.name
    }
}