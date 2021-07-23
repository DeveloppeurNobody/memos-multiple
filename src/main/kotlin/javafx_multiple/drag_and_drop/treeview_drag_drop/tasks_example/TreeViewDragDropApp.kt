package javafx_multiple.drag_and_drop.treeview_drag_drop.tasks_example

import javafx.application.Application
import javafx.collections.ObservableList
import javafx.scene.Scene
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.layout.StackPane
import javafx.stage.Stage

class TreeViewDragDropApp : Application() {

    override fun start(primaryStage: Stage?) {

        val rootItem: TreeItem<TaskNode> = TreeItem<TaskNode>(TaskNode("Tasks"));
        rootItem.isExpanded = true;

        val children: ObservableList<TreeItem<TaskNode>> = rootItem.children;
        children.add(TreeItem(TaskNode("do laundry")))
        children.add(TreeItem(TaskNode("get groceries")))
        children.add(TreeItem(TaskNode("drink beer")))
        children.add(TreeItem(TaskNode("defrag hard drive")))
        children.add(TreeItem(TaskNode("walk dog")))
        children.add(TreeItem(TaskNode("buy beer")))

        val tree = TreeView<TaskNode>(rootItem);
        tree.cellFactory = TaskCellFactory();


        var root = StackPane();
        root.children.add(tree);

        var scene = Scene(root, 600.0, 600.0);

        primaryStage?.title = "TreeViewDragDropApp";
        primaryStage?.scene = scene;
        primaryStage?.show();
    }
}

fun main(args: Array<String>) {
    Application.launch(TreeViewDragDropApp::class.java, *args);
}