package javafx_multiple.drag_and_drop.treeview_drag_drop.tasks_example

import javafx.scene.control.TreeCell
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.*
import javafx.util.Callback


class TaskCellFactory : Callback<TreeView<TaskNode>, TreeCell<TaskNode>> {
    companion object {
        val JAVA_FORMAT = DataFormat("application/x-java-serialized-object");
        const val DROP_HINT_STYLE = """
            -fx-border-color: #eea82f; 
            -fx-border-width: 0 0 2 0; 
            -fx-padding: 3 3 1 3
        """;
        val TASKS_IMAGE = Image(javaClass.getResource("/images/tasks.png").toExternalForm());
        val PIN_IMAGE = Image(javaClass.getResource("/images/pin.png").toExternalForm());
    }

    var dropZone: TreeCell<TaskNode>? = null;
    var draggedItem: TreeItem<TaskNode>? = null;

    override fun call(treeView: TreeView<TaskNode>?): TreeCell<TaskNode> {
        var cell = object: TreeCell<TaskNode>() {

            override fun updateItem(item: TaskNode?, empty: Boolean) {
                super.updateItem(item, empty);
                if (item == null) return;

                var iv1 = ImageView();
                if (item.name.equals("Tasks")) { iv1.image = TASKS_IMAGE; }
                else { iv1.image = PIN_IMAGE; }

                graphic = iv1;
                text = item.name;
            }
        };

        cell.setOnDragDetected { event: MouseEvent -> dragDetected(event, cell, treeView); }
        cell.setOnDragOver { event: DragEvent -> dragOver(event, cell, treeView); }
        cell.setOnDragDropped { event: DragEvent -> drop(event, cell, treeView); }
        cell.setOnDragDone { event: DragEvent -> clearDropLocation(); }

        return cell;
    }

    private fun dragDetected(event: MouseEvent, treeCell: TreeCell<TaskNode>, treeView: TreeView<TaskNode>?) {
        draggedItem = treeCell.treeItem

        // root can't be dragged
        if (draggedItem?.parent == null) return
        val db = treeCell.startDragAndDrop(TransferMode.MOVE)
        val content = ClipboardContent()
        content[JAVA_FORMAT] = draggedItem!!.value
        db.setContent(content)
        db.dragView = treeCell.snapshot(null, null)
        event.consume()
    }

    private fun dragOver(event: DragEvent, treeCell: TreeCell<TaskNode>, treeView: TreeView<TaskNode>?) {
        if (!event.dragboard.hasContent(JAVA_FORMAT)) return
        val thisItem = treeCell.treeItem

        // can't drop on itself
        if (draggedItem == null || thisItem == null || thisItem === draggedItem) return;

        // ignore if this is the root
        if (draggedItem!!.parent == null) {
            clearDropLocation();
            return;
        }

        event.acceptTransferModes(TransferMode.MOVE)
        if (dropZone != treeCell) {
            clearDropLocation();
            dropZone = treeCell;
            dropZone!!.style = DROP_HINT_STYLE;
        }
    }

    private fun drop(event: DragEvent, treeCell: TreeCell<TaskNode>, treeView: TreeView<TaskNode>?) {
        val db = event.dragboard
        val success = false
        if (!db.hasContent(JAVA_FORMAT)) return
        val thisItem = treeCell.treeItem
        val droppedItemParent = draggedItem!!.parent

        // remove from previous location
        droppedItemParent.children.remove(draggedItem)

        // dropping on parent node makes it the first child
        if (droppedItemParent == thisItem) {
            thisItem.children.add(0, draggedItem)
            treeView?.selectionModel?.select(draggedItem)
        } else {
            // add to new location
            val indexInParent = thisItem.parent.children.indexOf(thisItem)
            thisItem.parent.children.add(indexInParent + 1, draggedItem)
        }
        treeView?.selectionModel?.select(draggedItem)
        event.isDropCompleted = success
    }


    private fun clearDropLocation() {
        if (dropZone != null) dropZone?.style = "";
    }
}


