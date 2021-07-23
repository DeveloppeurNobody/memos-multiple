package nio2.chap05_filevisitor

import java.io.IOException
import java.nio.file.*
import java.util.*

object FileVisitorApp09ListTree {
    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class
    )
    fun main(args: Array<String>) {
        var listDir: Path = Paths.get("/home/ryu");
        var walk: ListTree = ListTree();
        var opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);

        Files.walkFileTree(listDir, opts, Int.MAX_VALUE, walk);
    }

    class ListTree : SimpleFileVisitor<Path>() {
        override fun postVisitDirectory(dir: Path?, exc: IOException?): FileVisitResult {
            println("Visited directory: $dir");
            return FileVisitResult.CONTINUE;
        }

        override fun visitFileFailed(file: Path?, exc: IOException?): FileVisitResult {
            println(exc);
            return FileVisitResult.CONTINUE;
        }
    }
}