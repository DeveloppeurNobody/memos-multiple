package swingtuto.defaultmutabletree

import java.io.File
import java.util.*
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel

/**
 * Custom DefaultTreeModel without Jtree (swing component)
 */
class MyDefaultTreeModel(var dir: File) {
    lateinit var treeModel: DefaultTreeModel;
    //lateinit var root: DefaultMutableTreeNode;


    init {
        treeModel = DefaultTreeModel(addNodes(null, dir));
    }

    private fun addNodes(currentParent: DefaultMutableTreeNode?, dir: File): DefaultMutableTreeNode {
        var currentPath: String = dir.path;
        var currentDir: DefaultMutableTreeNode = DefaultMutableTreeNode(currentPath);

        currentParent?.add(currentDir);

        var ol: MutableList<String> = mutableListOf();
        var tmp: Array<String> = dir.list();

        tmp.forEach { ol.add(it) };
        Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);

        var f: File;
        var files: MutableList<Any> = mutableListOf();
        ol.forEach {
            var thisObject = it;
            var newPath: String = if (currentPath.equals(".")) {
                thisObject;
            } else {
                "$currentPath${File.separator}$thisObject";
            }

            f = File(newPath);
            if (f.isDirectory) {
                addNodes(currentDir, f);
            }
            else {
                files.add(thisObject);
            }
        }

        files.forEach { currentDir.add(DefaultMutableTreeNode(it)) }
        return currentDir;
    }
}