package javafx_multiple.cell.treeview_cell_factory

import javafx.application.Application
import javafx.beans.property.SimpleStringProperty
import javafx.scene.Scene
import javafx.scene.control.TextField
import javafx.scene.control.TreeCell
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Stage


class TreeViewCellFactoryApp : Application() {
    var employees = listOf(
        Employee("Jacob Smith", "Accounts Department"),
        Employee("Isabella Johnson", "Accounts Department"),
        Employee("Mike Graham", "IT Support"),
        Employee("Judy Mayer", "IT Support"),
        Employee("Gregory Smith", "IT Support")
    );

    var rootNode: TreeItem<String> = TreeItem("My Company Human Resources");

    override fun start(stage: Stage) {
        rootNode.isExpanded = true
        for (employee in employees) {
            val empLeaf = TreeItem(employee.getName())
            var found = false
            for (depNode in rootNode.children) {
                if (depNode.value.contentEquals(employee.getDepartment())) {
                    depNode.children.add(empLeaf)
                    found = true
                    break
                }
            }
            if (!found) {
                val depNode = TreeItem(employee.getDepartment())
                rootNode.children.add(depNode)
                depNode.children.add(empLeaf)
            }
        }

        stage.title = "Tree View Sample"
        val box = VBox()
        val scene = Scene(box, 400.0, 300.0)
        scene.fill = Color.LIGHTGRAY

        val treeView = TreeView(rootNode)
        treeView.isEditable = true
        treeView.setCellFactory { p: TreeView<String> -> TextFieldTreeCellImpl() }

        box.children.add(treeView)
        stage.scene = scene
        stage.show()
    }
}

private class TextFieldTreeCellImpl : TreeCell<String>() {
    private var textField: TextField = TextField();
    override fun startEdit() {
        super.startEdit()
        if (textField == null) {
            createTextField()
        }
        text = ""
        graphic = textField
        textField.selectAll()
    }

    override fun cancelEdit() {
        super.cancelEdit()
        text = item
        graphic = treeItem.graphic
    }

    override fun updateItem(item: String, empty: Boolean) {
        super.updateItem(item, empty?: false)
        if (empty?: false) {
            text = ""
            graphic = null
        } else {
            if (isEditing) {
                if (textField != null) {
                    textField.text = string
                }
                text = ""
                graphic = textField
            } else {
                text = string
                graphic = treeItem.graphic
            }
        }
    }

    private fun createTextField() {
        textField = TextField(string)
        textField.setOnKeyReleased { t: KeyEvent ->
            if (t.code === KeyCode.ENTER) {
                commitEdit(textField.text)
            } else if (t.code === KeyCode.ESCAPE) {
                cancelEdit()
            }
        }
    }

    private val string: String
        private get() = if (item == null) "" else item.toString()
}

class Employee(name: String, department: String) {
    private val name: SimpleStringProperty = SimpleStringProperty(name)
    private val department: SimpleStringProperty = SimpleStringProperty(department)

    fun getName(): String {
        return name.get()
    }

    fun setName(fName: String?) {
        name.set(fName)
    }

    fun getDepartment(): String {
        return department.get()
    }

    fun setDepartment(fName: String?) {
        department.set(fName)
    }

}