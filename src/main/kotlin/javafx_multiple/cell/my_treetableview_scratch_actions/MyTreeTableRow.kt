package javafx_multiple.cell.my_treetableview_scratch_actions

import javafx.scene.control.TreeItem
import javafx.scene.control.TreeTableRow
import javafx.scene.control.TreeTableView
import javafx.scene.input.*

class MyTreeTableRow(var parent: TreeTableView<Fichier>? = null) : TreeTableRow<Fichier>() {
    companion object {
        var directory = false;
    }

    init {
        // detect user start drag
        setOnDragDetected {
            println("onDragDetected")
            val fichier = this.item;
            val dragboard = startDragAndDrop(*TransferMode.ANY);
            val content = ClipboardContent();
            content.putString(fichier.name);
            dragboard.setContent(content);
            it.consume();
        }

        setOnDragOver {
            println("onDragOver");
            // data is dragged over the target
            // accept only if it is not dragged from the same node and if it has a string data
            if (it.gestureSource != this && it.dragboard.hasString()) { it.acceptTransferModes(TransferMode.MOVE) }
            it.consume();
        }
        setOnDragEntered {
            println("onDragEntered");
            style = "-fx-background: orange;"
        }
        setOnDragDropped {
            val dragboard = it.dragboard
            println("onDragDropped #content: ${dragboard.getContent(DataFormat.PLAIN_TEXT)}");
            val success = false
            println("root in parent: ${parent?.root?.value?.name}")
            val fichier = Fichier(dragboard.getContent(DataFormat.PLAIN_TEXT).toString());
            fichier.isDirectory = directory;
            if (fichier.isDirectory) {
                println("isDirectory: TRUE; #directory: $directory")
                treeItem.children.add(TreeItem(fichier))
            } else {
                println("isDirectory: FALSE; #directory: $directory")
                treeItem.parent.children.add(TreeItem(fichier))
            }
            style = "-fx-background: green;";
        }
        setOnDragExited {
            println("onDragExited");
            style = "";
        }
    }

    override fun updateItem(item: Fichier?, empty: Boolean) {
        super.updateItem(item, empty);

        println("updateItem()")

        if (item != null && !empty) {

        }
    }


}