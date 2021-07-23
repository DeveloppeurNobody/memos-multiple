package swingtuto.defaultmutabletree

import java.awt.BorderLayout
import java.awt.Color
import java.awt.Container
import java.awt.Dimension
import java.io.File
import javax.swing.*
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeCellRenderer

/**
 * FileTreeWithPathsOnDirs that uses DefaultMutableTreeNode as model
 * it shows path on each dir
 * src/
 * -----src/main
 * -------------src/main/java
 */

fun main(av: Array<String>) {
//    var p: Path = Paths.get()


    SwingUtilities.invokeLater {
        var frame: JFrame = JFrame("FileTreeWithPathsOnDirs");
        frame.foreground = Color.BLACK;
        frame.background = Color.LIGHT_GRAY;
        var cp: Container = frame.contentPane;
        if (av.isEmpty()) {
            cp.add(FileTreeWithPathsOnDirs(File(".")));
        } else {
            cp.layout = BoxLayout(cp, BoxLayout.X_AXIS);
            for (i in av.indices) {
                cp.add(FileTreeWithPathsOnDirs(File(av[i])));
            }
        }
        frame.pack();
        frame.isVisible = true;
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE;
    }
}

class FileTreeWithPathsOnDirs(var dir: File) : JPanel() {
    lateinit var tree: JTree;
    init {
        layout = BorderLayout();
        tree = JTree(addNodes(null, dir));
        tree.addTreeSelectionListener {
            var node: DefaultMutableTreeNode = it.path
                .lastPathComponent as DefaultMutableTreeNode;
            println("You selected $node");
        }

        var renderer: DefaultTreeCellRenderer = tree.cellRenderer as DefaultTreeCellRenderer;
        with(renderer) {
            leafIcon = null;
            closedIcon = null;
            openIcon = null;
        }
        var scrollPane: JScrollPane = JScrollPane();
        scrollPane.viewport.add(tree);
        add(BorderLayout.CENTER, scrollPane);
    }

    private fun addNodes(curTop: DefaultMutableTreeNode?, dir: File): DefaultMutableTreeNode {
        var curPath: String = dir.path;
        var curDir: DefaultMutableTreeNode = DefaultMutableTreeNode(curPath);
        curTop?.add(curDir);
        var ol: MutableList<String> = mutableListOf<String>();
        var tmp: Array<String> = dir.list();
        for (i in tmp.indices) {
            ol.add(tmp[i]);
        }
        ol.sortWith(String.CASE_INSENSITIVE_ORDER);
        var f: File;
        var files: MutableList<Any> = mutableListOf();
        for (i in 0 until ol.size) {
            var thisObject: String = ol.elementAt(i);
            var newPath: String;
            newPath = if (curPath.equals(".")) {
                thisObject;
            } else {
                curPath + File.separator + thisObject;
            }

            f = File(newPath);
            if ( f.isDirectory ) {
                addNodes(curDir, f);
            } else {
                files.add(thisObject);
            }
        }
        for (fnum in 0 until files.size) {
            curDir.add(DefaultMutableTreeNode(files.elementAt(fnum)));
        }
        return curDir;
    }

    override fun getMinimumSize(): Dimension {
        return Dimension(200, 400);
    }

    override fun getPreferredSize(): Dimension {
        return Dimension(200, 400);
    }
}