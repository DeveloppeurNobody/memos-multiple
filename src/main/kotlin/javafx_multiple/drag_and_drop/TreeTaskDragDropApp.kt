package javafx_multiple.drag_and_drop

import javafx.application.Application
import javafx.collections.ObservableList
import javafx.scene.Scene
import javafx.scene.control.TreeCell
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.*
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import javafx.util.Callback
import java.io.Serializable
import java.util.*

class TreeTaskDragDropApp : Application() {

    override fun start(primaryStage: Stage?) {
        val rootItem = TreeItem<TaskNode>(TaskNode("Tasks"));
        rootItem.isExpanded = true;


        val children: ObservableList<TreeItem<TaskNode>> = rootItem.children;
        with(children) {
            add(TreeItem(TaskNode("do laundry")))
            add(TreeItem(TaskNode("get groceries")))
            add(TreeItem(TaskNode("drink beer")))
            add(TreeItem(TaskNode("defrag hard drive")))
            add(TreeItem(TaskNode("walk dog")))
            add(TreeItem(TaskNode("buy beer")))
        }

        val tree = TreeView<TaskNode>(rootItem);
        tree.cellFactory = TaskCellFactory();

        val root = StackPane();
        root.children.add(tree);

        primaryStage?.title = "Tree Task Drag rop Example";
        primaryStage?.scene = Scene(root, 300.0, 250.0);
        primaryStage?.show();
    }
}

fun main(args: Array<String>) {
    Application.launch(TreeTaskDragDropApp::class.java, *args);
}

data class TaskNode(var name: String = "") : Serializable

class TaskCellFactory : Callback<TreeView<TaskNode>, TreeCell<TaskNode>> {
    companion object {
        val JAVA_FORMAT = DataFormat("application/x-java-serialized-object")
        const val DROP_HINT_STYLE = "-fx-border-color: #eea82f; -fx-border-width: 0 0 2 0; -fx-padding: 3 3 1 3"
        val TASKS_IMAGE = Image(javaClass.getResource("/images/tasks.png").toExternalForm());
        val PIN_IMAGE = Image(javaClass.getResource("/images/pin.png").toExternalForm());
    }

    var dropZone: TreeCell<TaskNode>? = null;
    var draggedItem: TreeItem<TaskNode>? = null;

    override fun call(treeView: TreeView<TaskNode>?): TreeCell<TaskNode> {
        val cell = object: TreeCell<TaskNode>() {
            override fun updateItem(item: TaskNode?, empty: Boolean) {
                super.updateItem(item, empty);
                if (item == null || !empty) return;

                val iv1 = ImageView();
                if (item.name.equals("Tasks")) { iv1.image = TASKS_IMAGE; }
                else { iv1.image = PIN_IMAGE; }

                graphic = iv1;
                text = item.name;
            }
        }

        cell.setOnDragDetected { handleDragDetected(it, cell, treeView); }
        cell.setOnDragOver { handleDragOver(it, cell, treeView); }
        cell.setOnDragDropped { handleDragDropped(it, cell, treeView); }
        cell.setOnDragDone { clearDropLocation(); }

        return cell;
    }

    private fun handleDragDetected(event: MouseEvent?, cell: TreeCell<TaskNode>, treeView: TreeView<TaskNode>?) {
        val draggedItem = cell.treeItem;

        // root can't be dragged
        if (draggedItem.parent == null) return;

        val dragboard = cell.startDragAndDrop(TransferMode.MOVE);

        val content = ClipboardContent();
        content.put(JAVA_FORMAT, draggedItem.value);

        dragboard.setContent(content);
        dragboard.dragView = cell.snapshot(null, null);

        event?.consume();
    }

    private fun handleDragOver(event: DragEvent, cell: TreeCell<TaskNode>, treeView: TreeView<TaskNode>?) {
        if (!event.dragboard.hasContent(JAVA_FORMAT)) return;
        val thisItem = cell.treeItem;

        // can't drop on itself
        if (draggedItem == null || thisItem == null || thisItem == draggedItem) return;

        // ignore if this is the root
        if (draggedItem?.parent == null) {
            clearDropLocation();
            return;
        }

        event.acceptTransferModes(TransferMode.MOVE);
        if (!Objects.equals(dropZone, cell)) {
            clearDropLocation();
            dropZone = cell;
            dropZone?.style = DROP_HINT_STYLE;
        }
    }

    private fun handleDragDropped(event: DragEvent, cell: TreeCell<TaskNode>, treeView: TreeView<TaskNode>?) {
        val dragboard = event.dragboard;
        var success = false;

        if (!dragboard.hasContent(JAVA_FORMAT)) return;

        val thisItem = cell.treeItem;
        val droppedItemParent = draggedItem?.parent;

        // remove from previous location
        droppedItemParent?.children?.remove(draggedItem);

        // dropping on parent node makes it the first child
        if (Objects.equals(droppedItemParent, thisItem)) {
            thisItem.children.add(0, draggedItem);
            treeView?.selectionModel?.select(draggedItem);
        } else {
            // add to new location
            val indexInParent = thisItem.parent.children.indexOf(thisItem);
            thisItem.parent.children.add(indexInParent + 1, draggedItem);
        }
        treeView?.selectionModel?.select(draggedItem);

        event.isDropCompleted = success;
    }

    private fun clearDropLocation() { dropZone?.style = ""; }
}