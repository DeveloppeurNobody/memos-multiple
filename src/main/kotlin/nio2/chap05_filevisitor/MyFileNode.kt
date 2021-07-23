package nio2.chap05_filevisitor

import java.lang.StringBuilder
import java.nio.file.DirectoryStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*


object MyFileNode {
    var rootPath = Paths.get("/home/ryu/raf");
    var rootNode: MyFileNode.Node = MyFileNode.Node(rootPath.fileName.toString(), rootPath, false)

    @JvmStatic
    fun main(args: Array<String>) {
        addNodes(rootNode.path, rootNode);
        //addNodesWithDepth(rootNode.path, rootNode, 0);
        //dfsUsingStack(rootNode);

        printTree(rootNode, 0);
    }

    /**
     * Model that represent each node in tree
     * whether it is a dir or file
     */
    data class Node(var name: String,
                    var path: Path,
                    var visited: Boolean = false,
                    var childs: MutableList<Node> = mutableListOf())

    /**
     * check if path has childs
     * if it's a directory => we add all (childs) sub-directories as nodes to parent node
     * ( the trick is to iterate over childs of nodes not of the directory )
     * we iterate over parent.childs nodes and recall addNodes( with each child as subparent)
     */
    private fun addNodes(path: Path, parent: Node) {
        if (Files.isDirectory(path)) {
            var dirStream = Files.newDirectoryStream(path) as DirectoryStream<Path>
            dirStream.forEach {
                parent.childs.add(Node(it.fileName.toString(), it))
            }
            parent.childs
                .forEach {
                    addNodes(it.path, it)
                }
        } else {
            parent.childs.add(Node(path.fileName.toString(), path));
        }
    }

    /**
     * method for checking
     * like addNodes with deep
     * to print each dir and nodes
     * during construction of tree
     */
    private fun addNodesWithDepth(path: Path, parent: Node, depth: Int) {
        if (Files.isDirectory(path)) {
            var dirStream = Files.newDirectoryStream(path) as DirectoryStream<Path>
            println(spacesForDepth(depth) + " > " + parent.name);
            dirStream.forEach {
                parent.childs.add(Node(it.fileName.toString(), it))
            }

            parent.childs
                .forEach {
                    addNodesWithDepth(it.path, it, depth + 1)
                }
        } else {
            println(spacesForDepth(depth) + " - " + parent.name);
            parent.childs.add(Node(path.fileName.toString(), path));
        }
    }

    /**
     * we print rootNode at first time, then if rootNode has children
     * we iterate over childs of rootNode and for each child
     * we recall printTree with child as argument (play as root for the nextsubtree)
     * @param node rootNode first time and then child for recursive calls
     * @param depth 0 first time and +1 for each recursive call to track the deep of tree
     */
    private fun printTree(node: Node, depth: Int) {
        if (Files.isDirectory(node.path)) {
            println(spacesForDepth(depth) + " > " + node.name)
            if (node.childs.isNotEmpty()) {
                node.childs
                    .forEach { printTree(it, depth + 1) }
            }
        } else {
            println(spacesForDepth(depth) + " - " + node.name)
        }
    }

    /**
     * add space in accordance with depth
     * exp: depth = 1 we got " " depth = 4 we got: "    ".
     * @param depth represent depth of tree
     * @return space according to depth
     */
    private fun spacesForDepth(depth: Int): String {
        var builder = String();
        for (i in 0 until depth) {
            builder += " ";
        }
        return builder;
    }
}