package nio2.chap05_filevisitor

import java.io.File
import java.io.IOException
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes
import java.util.*
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel


object FileVisitorListTreeWithDefaultMutableTreeNode {
    var rootNode: DefaultMutableTreeNode = DefaultMutableTreeNode();
    lateinit var tree: DefaultTreeModel;
    var currentParent: Path? = null;
    lateinit var currentNode: DefaultMutableTreeNode;
    var mapNodes: MutableMap<String, DefaultMutableTreeNode> = mutableMapOf();
    lateinit var nodeParent: DefaultMutableTreeNode;

    data class Node(var path: Path) {
        var visited: Boolean = false;
        var neighbours: MutableList<Node> = mutableListOf();
    }
    var stack: Stack<Node> = Stack();

    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class
    )
    fun main(args: Array<String>) {
        var listDir: Path = Paths.get("/home/ryu/raf");

        var walk: ListTree = ListTree();
        var opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
        rootNode= DefaultMutableTreeNode(listDir);
        var nodes: MutableList<DefaultMutableTreeNode> = mutableListOf();

        mapNodes.put("${listDir.nameCount}", rootNode);



//
//        var r: DefaultMutableTreeNode = DefaultMutableTreeNode(Paths.get("/"));
//        for (i in 1 until  listDir.nameCount) {
//            println("listDir.getName(): ${listDir.getName(i)}")
//            r.add(DefaultMutableTreeNode(listDir.getName(i)));
//        }
//
        Files.walkFileTree(listDir, opts, Int.MAX_VALUE, walk);

    //    println("------------ stack");
    //    stack.forEachIndexed { index, defaultMutableTreeNode -> println("index: $index | node: ${defaultMutableTreeNode.userObject}") }
       // mapNodes.forEach { t, u -> println("t: $t | ${u.userObject}") }
//
//        println("children...")
//        rootNode.children()
//            .asIterator()
//            .forEachRemaining(::println);

        //tree = DefaultTreeModel(rootDir);
    }



    class ListTree : SimpleFileVisitor<Path>() {
        var firstCall: Boolean = true;

        override fun postVisitDirectory(dir: Path?, exc: IOException?): FileVisitResult {
            println("--- [ postDirectory ] $dir")

//            rootNode.add(DefaultMutableTreeNode(dir!!.fileName));
//            with(dir!!) {
//                println("directory indicated by path: $fileName");
//                println("Root of this path: $root");
//                println("Parent: $parent");
//                println("Number of name elements is path: $nameCount");
//                for(i in 0 until nameCount) {
//                    println("Name element: $i is ${getName(i)}");
//                }
//                println("Subpath (0,3): ${subpath(0, 3)}");
//            }
            //println()
            return FileVisitResult.CONTINUE;
        }

        override fun visitFile(file: Path?, attrs: BasicFileAttributes?): FileVisitResult {
            rootNode.add(DefaultMutableTreeNode(file!!.fileName));
            return FileVisitResult.CONTINUE
        }

        override fun visitFileFailed(file: Path?, exc: IOException?): FileVisitResult {
            println(exc);
            return FileVisitResult.CONTINUE;
        }

        override fun preVisitDirectory(dir: Path?, attrs: BasicFileAttributes?): FileVisitResult {


            return FileVisitResult.CONTINUE;
        }


    }
}