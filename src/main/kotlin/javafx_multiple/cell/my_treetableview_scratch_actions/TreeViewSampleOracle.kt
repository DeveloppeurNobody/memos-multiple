package javafx_multiple.cell.my_treetableview_scratch_actions

import javafx.application.Application
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.TextField
import javafx.scene.control.TreeCell
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Stage
import java.util.*

class TreeViewSampleOracle : Application() {
    val rootIcon: Node = ImageView(Image(javaClass.getResourceAsStream("/images/root.png")));
    val depIcon = Image(javaClass.getResourceAsStream("/images/department.png"));

    val employees: List<Employee> = listOf(
        Employee("Ethan Williams", "Sales Department"),
        Employee("Emma Jones", "Sales Department"),
        Employee("Michael Brown", "Sales Department"),
        Employee("Anna Black", "Sales Department"),
        Employee("Rodger York", "Sales Department"),
        Employee("Susan Collins", "Sales Department"),
        Employee("Mike Graham", "IT Support"),
        Employee("Judy Mayer", "IT Support"),
        Employee("Gregory Smith", "IT Support"),
        Employee("Jacob Smith", "Accounts Department"),
        Employee("Isabella Johnson", "Accounts Department")
    );



    override fun start(primaryStage: Stage?) {
        val rootNode = TreeItem("MyCompany Human Resources", rootIcon);
        rootNode.isExpanded = true;
        employees.forEach { employee ->
            val emplLeaf = TreeItem(employee.getName());
            var found = false;
            rootNode
                .children
                .forEach { depNode ->
                    if (depNode.value.contentEquals(employee.getDepartment().toString())) {
                        depNode.children.add(emplLeaf);
                        found = true;
                        return@forEach;
                    }
                }

            if (!found) {
                val depNode = TreeItem(employee.getDepartment(), ImageView(depIcon));
                rootNode.children.add(depNode);
                depNode.children.add(emplLeaf);
            }
        }

        primaryStage!!.title = "Tree View Sample";
        val vbox = VBox();
        var scene = Scene(vbox, 400.0, 300.0);
        scene.fill = Color.LIGHTGRAY;

        val treeView = TreeView(rootNode);
        treeView.isEditable = true;
        treeView.setCellFactory { TextFieldTreeCellImpl(); }

        vbox.children.add(treeView);
        primaryStage?.scene = scene;
        primaryStage?.show();
    }

    inner class TextFieldTreeCellImpl : TreeCell<String>() {
        private var textField: TextField? = null;

        override fun startEdit() {
            super.startEdit();

            if (textField == null) { createTextField(); }
            text = null;
            graphic = textField;
            textField!!.selectAll();
        }

        override fun cancelEdit() {
            super.cancelEdit();
            text = item as String;
            graphic = treeItem.graphic;
        }

        override fun updateItem(item: String?, empty: Boolean) {
            super.updateItem(item, empty);

            if (empty) {
                text = null;
                graphic = null;
            } else {
                if (isEditing) {
                    if (textField != null) {
                        textField?.text = getString();
                    }
                    text = null;
                    graphic = treeItem.graphic;
                }
                else {
                    text = getString();
                    graphic = treeItem.graphic;
                }
            }
        }

        private fun createTextField() {
            textField = TextField(getString());
            textField?.setOnKeyReleased {
                if (it.code == KeyCode.ENTER) {
                    commitEdit(textField?.text);
                } else if (it.code == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            }
        }

        private fun getString(): String {
            return item?: "";
        }
    }

    class Employee {
        private var name: StringProperty?;
        private var department: StringProperty?;

        constructor(name: String = "", department: String = "") {
            this.name = SimpleStringProperty(name);
            this.department = SimpleStringProperty(department);
        }

        fun getName(): String? { return name!!.get(); }
        fun setName(value: String) { name!!.set(value);}

        fun getDepartment(): String? { return department!!.get(); }
        fun setDepartment(value: String) { department!!.set(value); }
    }
}

fun main(args: Array<String>) {
    Application.launch(TreeViewSampleOracle::class.java, *args);
}