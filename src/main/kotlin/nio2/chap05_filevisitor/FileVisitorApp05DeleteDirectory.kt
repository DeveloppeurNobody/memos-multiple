package nio2.chap05_filevisitor

import java.io.IOException
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes
import java.util.*

object FileVisitorApp05DeleteDirectory {
    @JvmStatic
    @Throws(
     Exception::class,
     IOException::class
    )
    fun main(args: Array<String>) {
        var directory: Path = Paths.get("/home/ryu/raf");
        var walk: DeleteDirectory = DeleteDirectory();
        var opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);

        Files.walkFileTree(directory, opts, Int.MAX_VALUE, walk);

        println("return true")
    }

    class DeleteDirectory : FileVisitor<Any> {

        @Throws(IOException::class)
        fun deleteFileByFile(file: Path): Boolean {
            return Files.deleteIfExists(file);
        }

        @Throws(IOException::class)
        override fun preVisitDirectory(dir: Any?, attrs: BasicFileAttributes?): FileVisitResult {
            return FileVisitResult.CONTINUE;
        }

        @Throws(IOException::class)
        override fun visitFile(file: Any?, attrs: BasicFileAttributes?): FileVisitResult {
            var success: Boolean = deleteFileByFile(file as Path);

            if (success) {
                println("Deleted: ${file as Path}");
            }
            else {
                println("Not Deleted: ${file as Path}");
            }

            return FileVisitResult.CONTINUE;
        }

        override fun visitFileFailed(file: Any?, exc: IOException?): FileVisitResult {
            return FileVisitResult.CONTINUE;
        }

        @Throws(IOException::class)
        override fun postVisitDirectory(dir: Any?, exc: IOException?): FileVisitResult {
            if (exc == null) {
                println(">>> postVisitDirectory().Visited: ${dir as Path}");
                var success: Boolean = deleteFileByFile(dir);

                if (success) {
                    println(">>>>>> postVisitDirectory().Deleted: ${dir as Path}");
                }
                else {
                    println(">>>>>> postVisitDirectory().Not Deleted: ${dir as Path}");
                }
            }
            else {
                throw exc;
            }
            return FileVisitResult.CONTINUE;
        }
    }
}