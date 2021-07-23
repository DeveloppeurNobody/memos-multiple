package nio2.chap00_mft.filesystem.tree

import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes

class TreeFilesVisitor : SimpleFileVisitor<Path>() {
    var index: Int = 0;
    var root: NodeDir? = null;

    override fun visitFile(file: Path?, attrs: BasicFileAttributes?): FileVisitResult {
        if (file != null) {
            root?.files
                    ?.put(file.fileName.toString(), file);
        }

        return FileVisitResult.CONTINUE;
    }

    override fun visitFileFailed(file: Path?, exc: IOException?): FileVisitResult {
        if (file != null) {
            root?.files
                    ?.put(file.fileName.toString(), file);
        }
        println(exc);
        return FileVisitResult.CONTINUE;
    }

    override fun preVisitDirectory(dir: Path?, attrs: BasicFileAttributes?): FileVisitResult {
        if (dir != null) {
            index++;
            if (root == null) {
                var node: NodeDir = NodeDir(dir);
                root = node;
            }
            else {
                var node: NodeDir = NodeDir(dir);
                node.parent = root;
                root?.childs
                        ?.put(dir.toString(), node);
                root = node;
            }
        }

        return FileVisitResult.CONTINUE;
    }

    override fun postVisitDirectory(dir: Path?, exc: IOException?): FileVisitResult {
        index--;
        if (root != null) {
            if (root?.parent != null) {
                root = root?.parent;
            }
        }
        return FileVisitResult.CONTINUE;
    }


    // for level in recurisve call
    var level: Int = 0;
    var levelString: String = "";
    var dirList: MutableList<String> = mutableListOf();

    fun printDirs(nodeDir: NodeDir, childs: MutableMap<String, NodeDir>): MutableList<String> {
        dirList.add(nodeDir.dirname.toString());
        level++;
        println("$level: ${nodeDir.dirname}");

        nodeDir.files
                .forEach { (t, u) -> println("+++ $t") }
        childs.forEach { (t, u) ->
            if (u.childs != null) {
                printDirs(u, u.childs);
            }
        }
        if (level > 0) {
            level--;
        };
        return dirList;
    }

    data class NodeDir(var dir: Path? = null) {
        var dirname: String? = null;
        var parent: NodeDir? = null;
        var childs: MutableMap<String, NodeDir> = mutableMapOf();
        var files: MutableMap<String, Path> = mutableMapOf();

        init {
            if (dir != null) {
                dirname = dir?.fileName.toString();
            }
        }

    }
}

