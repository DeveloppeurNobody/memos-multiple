package swingtuto.defaultmutabletree

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import javax.swing.tree.DefaultMutableTreeNode

fun main() {
    var myTree: MyDefaultMutableTreeNodeContainer = MyDefaultMutableTreeNodeContainer(Paths.get("/home/ryu/raf"));
    println("path: ${myTree.rootNode.userObject}");
    println("countChildren: ${myTree.rootNode.childCount}");
}

class MyDefaultMutableTreeNodeContainer(var dir: Path) : DefaultMutableTreeNode() {
    lateinit var rootNode: DefaultMutableTreeNode;
    init {
        if (dir != null) {
            rootNode = DefaultMutableTreeNode(dir);
            println(dir)
        }
    }
}