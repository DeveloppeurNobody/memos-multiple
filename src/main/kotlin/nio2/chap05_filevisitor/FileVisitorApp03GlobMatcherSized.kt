package nio2.chap05_filevisitor

import java.io.IOException
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes
import java.util.*

object FileVisitorApp03GlobMatcherSized {

    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class
    )
    fun main(args: Array<String>) {
        var glob: String = "*.jpg";
        var size: Long = 102400; // 100 kB in Bytes
        var fileTree: Path = Paths.get("/home/ryu/raf");
        var walk: SearchGlobSized = SearchGlobSized(glob, size);
        var opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);

        Files.walkFileTree(fileTree, opts, Int.MAX_VALUE, walk);
    }

    class SearchGlobSized(val glob: String,
                          val acceptedSize: Long
    ) : FileVisitor<Any> {
        val matcher: PathMatcher = FileSystems.getDefault()
            .getPathMatcher("glob:$glob");

        @Throws(IOException::class)
        fun search(file: Path) {
            var name: Path = file.fileName;
            var size: Long = Files.getAttribute(file, "basic:size") as Long;

            if (name != null
                && matcher.matches(name)
                && size <= acceptedSize) {
                println("Searched file was found: $name in ${file.toRealPath()} size: $size");
            }
        }

        override fun preVisitDirectory(dir: Any?, attrs: BasicFileAttributes?): FileVisitResult {
            return FileVisitResult.CONTINUE;
        }

        override fun visitFile(file: Any?, attrs: BasicFileAttributes?): FileVisitResult {
            search(file as Path);
            return FileVisitResult.CONTINUE;
        }

        override fun visitFileFailed(file: Any?, exc: IOException?): FileVisitResult {
            return FileVisitResult.CONTINUE;
        }

        override fun postVisitDirectory(dir: Any?, exc: IOException?): FileVisitResult {
            println("[postVisitDirectory] visited: ${dir as Path}");
            return FileVisitResult.CONTINUE;
        }
    }
}