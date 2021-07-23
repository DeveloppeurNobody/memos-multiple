package nio2.chap05_filevisitor

import java.io.IOException
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.attribute.FileTime
import java.util.*

object FileVisitorApp07MoveTreeWithDelete {
    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class
    )
    fun main(args: Array<String>) {
        var moveFrom: Path = Paths.get("/home/ryu/raf");
        var moveTo: Path = Paths.get("/home/ryu/raf_app07_movetree");

        var walk: MoveTree = MoveTree(moveFrom, moveTo);
        var opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);

        Files.walkFileTree(moveFrom, opts, Int.MAX_VALUE, walk);
    }

    class MoveTree(val moveFrom: Path,
                   val moveTo: Path) : FileVisitor<Any> {

        companion object {

            var time: FileTime? = null;

            @Throws(IOException::class)
            fun moveSubTree(moveFrom: Path, moveTo: Path) {
                try {
                    Files.move(moveFrom, moveTo, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
                } catch (ioe: IOException) {
                    System.err.println("unable to move $moveFrom [ $ioe ]");
                }
            }
        }

        @Throws(IOException::class)
        override fun postVisitDirectory(dir: Any?, exc: IOException?): FileVisitResult {
            var newDir: Path = moveTo.resolve(moveFrom.relativize(dir as Path));
            try {
                Files.setLastModifiedTime(newDir, time);
                Files.delete(dir);
            } catch (ioe: IOException) {
                System.err.println("Unable to copy all attributes to: $newDir [ $ioe ]");
            }
            return FileVisitResult.CONTINUE;
        }

        @Throws(IOException::class)
        override fun preVisitDirectory(dir: Any?, attrs: BasicFileAttributes?): FileVisitResult {
            println("Move directory: ${dir as Path}");
            var newDir: Path = moveTo.resolve(moveFrom.relativize(dir));
            try {
                Files.copy(dir, newDir, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
                time = Files.getLastModifiedTime(dir);
            } catch (ioe: IOException) {
                System.err.println("Unable to move $newDir [ $ioe ]");
                return FileVisitResult.SKIP_SUBTREE;
            }
            return FileVisitResult.CONTINUE;
        }

        @Throws(IOException::class)
        override fun visitFile(file: Any?, attrs: BasicFileAttributes?): FileVisitResult {
            println("Move file ${file as Path}");
            moveSubTree(file, moveTo.resolve(moveFrom.relativize(file)));
            return FileVisitResult.CONTINUE;
        }

        @Throws(IOException::class)
        override fun visitFileFailed(file: Any?, exc: IOException?): FileVisitResult {
            return FileVisitResult.CONTINUE;
        }
    }
}