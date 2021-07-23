package swingtuto.defaultmutabletree.runnable

import java.io.File
import java.nio.file.Paths
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTree
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel

//fun main(args: Array<String>) {
//    SwingUtilities.invokeLater(FileBrowser())
//}

/**
 * DefaultTreeModel with multiple runnable interfaces
 */
class FileBrowser : Runnable {
    var root: DefaultMutableTreeNode? = null
    var treeModel: DefaultTreeModel? = null
    var tree: JTree? = null
    override fun run() {
        val frame = JFrame("File Browser")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        val fileRoot = Paths.get("/home/ryu/raf").toFile()
        root = DefaultMutableTreeNode(FileNode(fileRoot))
        treeModel = DefaultTreeModel(root)
        tree = JTree(treeModel)
        tree!!.showsRootHandles = true
        val scrollPane = JScrollPane(tree)
        frame.add(scrollPane)
        frame.isLocationByPlatform = true
        frame.setSize(640, 480)
        frame.isVisible = true
        val ccn = CreateChildNodes(fileRoot, root!!)
        Thread(ccn).start()
    }

    inner class CreateChildNodes(
        private val fileRoot: File,
        private val root: DefaultMutableTreeNode) : Runnable {

        override fun run() {
            createChildren(fileRoot, root)
        }

        private fun createChildren(fileRoot: File, node: DefaultMutableTreeNode) {
            val files = fileRoot.listFiles() ?: return
            for (file in files) {
                val childNode = DefaultMutableTreeNode(FileNode(file))
                node.add(childNode)
                if (file.isDirectory) {
                    createChildren(file, childNode)
                }
            }
        }

    }

    inner class FileNode(private val file: File) {
        override fun toString(): String {
            val name = file.name
            return if (name == "") {
                file.absolutePath
            } else {
                name
            }
        }

    }
}