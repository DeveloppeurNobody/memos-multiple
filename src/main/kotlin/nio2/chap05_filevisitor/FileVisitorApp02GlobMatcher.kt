package nio2.chap05_filevisitor

import java.io.IOException
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes
import java.util.*

object FileVisitorApp02GlobMatcher {

    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class
    )
    fun main(args: Array<String>) {
        var glob: String = ".jpg";
        var fileTree: Path = Paths.get("/home/ryu/raf");
        var walk: SearchGlob = SearchGlob(glob);
        var opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);

        Files.walkFileTree(fileTree, opts, Int.MAX_VALUE, walk);
    }


    class SearchGlob(var glob: String) : FileVisitor<Any> {
        var matcher: PathMatcher = FileSystems.getDefault()
            .getPathMatcher("glob:$glob");

        @Throws(IOException::class)
        fun search(file: Path) {
            var name: Path? = file.fileName;
            if (name != null && matcher.matches(name)) {
                println("Searched file was found: $name in ${file.toRealPath()}");
            }
        }

        override fun preVisitDirectory(dir: Any?, attrs: BasicFileAttributes?): FileVisitResult {
            println("<<< [ preVisitDirectory ] ${dir as Path}");
            return FileVisitResult.CONTINUE;
        }

        override fun visitFile(file: Any?, attrs: BasicFileAttributes?): FileVisitResult {
            search(file as Path);
            return FileVisitResult.CONTINUE;
        }

        override fun visitFileFailed(file: Any?, exc: IOException?): FileVisitResult {
            println("[ visitFileFailed ]");
            return FileVisitResult.CONTINUE;
        }

        override fun postVisitDirectory(dir: Any?, exc: IOException?): FileVisitResult {
            println(">>> [ postVisitDirectory ] ${dir as Path}");
            return FileVisitResult.CONTINUE;
        }
    }
}