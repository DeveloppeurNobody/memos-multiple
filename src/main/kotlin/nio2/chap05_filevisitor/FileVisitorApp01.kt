package nio2.chap05_filevisitor

import java.io.IOException
import java.lang.Exception
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes
import java.util.*

object FileVisitorApp01 {
    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class
    )
    fun main(args: Array<String>) {
        var searchedFile: Path = Paths.get("rafa_2.jpg");
        var walk: Search = Search(searchedFile);
        var opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);

        var dirs: Iterable<Path> = FileSystems.getDefault()
            .rootDirectories;
        dirs.forEach {
            println("it: $it")
            if (!walk.found) {
                Files.walkFileTree(it, opts, Int.MAX_VALUE, walk);
            }
        }

        if (!walk.found) {
            println("The file $searchedFile was not found!")
        }

    }

    class Search(var searchedFile: Path,
                 var found: Boolean = false
    ) : FileVisitor<Any> {

        @Throws(
            Exception::class
        )
        fun search(filePath: Path) {
            var name: Path = filePath.fileName;
            if (name != null && name.equals(searchedFile)) {
                println("Searched file was found: $searchedFile in ${filePath.toRealPath()}")
                found = true;
            }
        }

        override fun postVisitDirectory(dir: Any?, exc: IOException?): FileVisitResult {
            println(">>>[ postVisitedDirectory ]: ${dir as Path}");
            return FileVisitResult.CONTINUE;
        }

        override fun visitFile(file: Any?, attrs: BasicFileAttributes?): FileVisitResult {
            println("[ visitFile ]")
            search(file as Path);
            if (found) {
                return FileVisitResult.CONTINUE;
            }
            else {
                return FileVisitResult.TERMINATE;
            }
        }

        @Throws(IOException::class)
        override fun visitFileFailed(file: Any?, exc: IOException?): FileVisitResult {
            println("[ visitFileFailed ]");
            return FileVisitResult.CONTINUE;
        }

        override fun preVisitDirectory(dir: Any?, attrs: BasicFileAttributes?): FileVisitResult {
            println("<<<[ preVisitedDirectory ]")
            return FileVisitResult.CONTINUE;
        }
    }
}