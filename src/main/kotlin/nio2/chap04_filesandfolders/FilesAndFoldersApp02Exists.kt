package nio2.chap04_filesandfolders

import java.io.IOException
import java.lang.Exception
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.LinkOption
import java.nio.file.Path

object FilesAndFoldersApp02Exists {

    @JvmStatic
    @Throws(
     Exception::class,
     IOException::class
    )
    fun main(args: Array<String>) {
        var path: Path = FileSystems.getDefault()
            .getPath("/home/ryu/raf/ts/2009", "AEGON.txt");

        var pathExists: Boolean = Files.exists(path, *arrayOf(LinkOption.NOFOLLOW_LINKS));
        var pathNotExists: Boolean = Files.notExists(path, *arrayOf(LinkOption.NOFOLLOW_LINKS));

        println("Exists ? $pathExists | Not exists ? $pathNotExists")
    }
}