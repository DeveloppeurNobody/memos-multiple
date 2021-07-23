package nio2.chap05_filevisitor

import java.io.IOException
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes
import java.util.*
import javax.swing.tree.TreeModel

object FileVisitorCustomFilesAsTree {
    @JvmStatic
    @Throws(
     Exception::class,
     IOException::class
    )
    fun main(args: Array<String>) {
        var listDir: Path = Paths.get("/home/ryu")
        var filesNames: MutableList<String> = mutableListOf();
        var dirNames: MutableList<String> = mutableListOf();
        var walk: TreeFiles = TreeFiles();
        var opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);

        Files.walkFileTree(listDir, opts, Int.MAX_VALUE, walk);

        walk.dirsPaths
            .forEach(::println)

        walk.filesPaths
            .forEach(::println);


    }

    class TreeFiles : SimpleFileVisitor<Path>() {
        var filesPaths: MutableList<String> = mutableListOf();
        var dirsPaths: MutableList<String> = mutableListOf();
        //var filesTree: TreeSet

        override fun preVisitDirectory(dir: Path?, attrs: BasicFileAttributes?): FileVisitResult {
            dirsPaths.add("dir: $dir");
            return FileVisitResult.CONTINUE;
        }

        override fun visitFile(file: Path?, attrs: BasicFileAttributes?): FileVisitResult {
            filesPaths.add("--- file: $file")
            return FileVisitResult.CONTINUE;
        }

        override fun visitFileFailed(file: Path?, exc: IOException?): FileVisitResult {
            println(exc);
            return FileVisitResult.CONTINUE;
        }

        override fun postVisitDirectory(dir: Path?, exc: IOException?): FileVisitResult {
            println("visited directory: $dir")
            return FileVisitResult.CONTINUE;
        }
    }


}