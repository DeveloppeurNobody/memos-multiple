package nio2.chap04_filesandfolders

import java.io.File
import java.io.IOException
import java.nio.file.FileSystems
import java.nio.file.Path

object FilesAndFoldersApp08RootDirectories {
    @JvmStatic
    @Throws(
     Exception::class,
     IOException::class
    )
    fun main(args: Array<String>) {

        // Java 6 solution
        println("*** Java 6")
        var roots: Array<File> = File.listRoots();
        roots.forEach(::println);


        // Java 7 solution
        println("*** Java 7")
        var dirs: Iterable<Path> = FileSystems.getDefault()
            .rootDirectories;
        dirs.forEach(::println);
    }
}