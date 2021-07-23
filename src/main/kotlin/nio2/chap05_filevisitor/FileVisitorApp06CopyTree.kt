package nio2.chap05_filevisitor

import java.io.IOException
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.attribute.FileTime
import java.util.*

object FileVisitorApp06CopyTree {
    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class
    )
    fun main(args: Array<String>) {
        var copyFrom: Path = Paths.get("/home/ryu/raf");
        var copyTo: Path = Paths.get("/home/ryu/raf__copy");

        var walk: CopyTree = CopyTree(copyFrom, copyTo);
        var opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);

        Files.walkFileTree(copyFrom, opts, Int.MAX_VALUE, walk);
    }

    class CopyTree(val copyFrom: Path,
                   val copyTo: Path
    ) : FileVisitor<Any> {

        companion object {
            @Throws(IOException::class)
            fun copySubTree(copyFrom: Path, copyTo: Path) {
                try {
                    Files.copy(copyFrom, copyTo, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
                } catch (ioe: IOException) {
                    System.err.println("Unable to copy [ $copyFrom ] [ $ioe ]");
                }
            }
        }

        @Throws(IOException::class)
        override fun preVisitDirectory(dir: Any?, attrs: BasicFileAttributes?): FileVisitResult {
            println("Copy directory: ${dir as Path}");
            var newDir: Path = copyTo.resolve(copyFrom.relativize(dir));

            try {
                Files.copy(dir, newDir, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
            } catch (ioe: IOException) {
                System.err.println(ioe);
                return FileVisitResult.SKIP_SUBTREE;
            }
            return FileVisitResult.CONTINUE;
        }

        @Throws(IOException::class)
        override fun visitFile(file: Any?, attrs: BasicFileAttributes?): FileVisitResult {
            println("Copy file: ${file as Path}");
            copySubTree(file, copyTo.resolve(copyFrom.relativize(file)));
            return FileVisitResult.CONTINUE;
        }

        @Throws(IOException::class)
        override fun visitFileFailed(file: Any?, exc: IOException?): FileVisitResult {
            if (exc is FileSystemLoopException) {
                System.err.println("Cycle was detected: ${file as Path}");
            }
            return FileVisitResult.CONTINUE;
        }

        @Throws(IOException::class)
        override fun postVisitDirectory(dir: Any?, exc: IOException?): FileVisitResult {
            if (exc == null) {
                var newDir: Path = copyTo.resolve(copyFrom.relativize(dir as Path));
                try {
                    var time: FileTime = Files.getLastModifiedTime(dir);
                    Files.setLastModifiedTime(newDir, time);
                } catch (ioe: IOException) {
                    System.err.println("Unable to copy all attributes to $newDir [ $ioe ]");
                }
            }
            else {
                throw exc;
            }
            return FileVisitResult.CONTINUE;
        }
    }
}