package swingtuto.treemodel

import java.io.File
import javax.swing.*
import javax.swing.event.TreeModelListener
import javax.swing.tree.TreeModel
import javax.swing.tree.TreePath

/**
 * TreeModel (not DefaultMutableTree)
 * with JTree and JSplitPanel (on right) for details of files
 */
data class FileTreeWithDetailsOnItRight(var directory: String) : JFrame() {
    lateinit var fileTree: JTree;
    lateinit var fileSystemModel: FileSystemModel;
    var fileDetailsTextArea = JTextArea();

    init {
        fileDetailsTextArea.isEditable = false;
        fileSystemModel = FileSystemModel(File(directory));
        fileTree = JTree(fileSystemModel);
        fileTree.addTreeSelectionListener {
            var file: File = fileTree.lastSelectedPathComponent as File;
            fileDetailsTextArea.text = getFileDetails(file);
        }
        fileTree.isEditable = true;

        val splitPane = JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT, true, JScrollPane(
                fileTree
            ), JScrollPane(fileDetailsTextArea)
        )
        contentPane.add(splitPane)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        setSize(640, 480)
        isVisible = true

    }

    fun getFileDetails(file: File?): String? {
        if (file == null) return ""
        val buffer = StringBuffer()
        with(buffer) {
            append("Name: ${file.name}\n");
            append("Path: ${file.path}\n");
            append("Size: ${file.length()}\n")
        }
        return buffer.toString()
    }

}

class FileSystemModel(val root: File) : TreeModel {
    lateinit var listeners: MutableList<Any>;

    override fun getRoot(): Any {
        return root;
    }

    override fun isLeaf(node: Any?): Boolean {
        var file: File = node as File;
        return file.isFile;
    }

    override fun getChildCount(parent: Any?): Int {
        var file: File = parent as File;
        if (file.isDirectory) {
            var fileList: Array<String> = file.list();
            if (fileList != null) {
                return file.list().size;
            }
        }
        return 0;
    }

    override fun removeTreeModelListener(l: TreeModelListener?) {

    }

    override fun valueForPathChanged(path: TreePath?, newValue: Any?) {
        var oldFile = path?.lastPathComponent as File;
        var fileParentPath: String = oldFile.parent;
        var newFileName = newValue as String;
        var targetFile: File = File(fileParentPath, newFileName);
        oldFile.renameTo(targetFile);
        var parent: File = File(fileParentPath);
        var changedChildrenIndices: Array<Int> = arrayOf(getIndexOfChild(parent, targetFile));
        var changedChildren: Array<Any> = arrayOf(targetFile);
       // fireTreeNodesChanged(path.parentPath, changedChildrenIndices, changedChildren);
    }

    override fun getIndexOfChild(parent: Any?, child: Any?): Int {
        var directory: File = parent as File;
        var file: File = child as File;
        var children: Array<String> = directory.list();
        children.forEach { if (file.name.equals(it)) {
            return children.indexOf(it);
        }
        }
        return -1;
    }


    override fun getChild(parent: Any?, index: Int): Any {
        var directory: File = parent as File;
        var children: Array<String> = directory.list();
        return TreeFile(directory, children[index]);
    }

    override fun addTreeModelListener(l: TreeModelListener?) {

    }

    inner class TreeFile(var parent: File, var child: String) : File(parent, child){
        override fun toString(): String {
            return this.name;
        }
    }
}