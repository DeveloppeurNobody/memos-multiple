package nio2.chap04_filesandfolders

import java.io.IOException
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path

object FilesAndFoldersApp07IsSameFile {
    @JvmStatic
    @Throws(
     Exception::class,
     IOException::class
    )
    fun main(args: Array<String>) {
        var path01: Path = FileSystems.getDefault()
            .getPath("/home/ryu/raf/ts/2009", "MutuaMadridOpen.txt");
        var path02: Path = FileSystems.getDefault()
            .getPath(System.getProperty("user.home"), "raf/ts/2009", "MutuaMadridOpen.txt");
        var path03: Path = FileSystems.getDefault()
            .getPath("/home/ryu/raf/ts/dummy/../2009", "MutuaMadridOpen.txt");

        try {
            var isSameFile12: Boolean = Files.isSameFile(path01, path02);
            var isSameFile13: Boolean = Files.isSameFile(path01, path03);
            var isSameFile23: Boolean = Files.isSameFile(path02, path03);
            println("is same file 1&2 ? $isSameFile12");
            println("is same file 1&3 ? $isSameFile13");
            println("is same file 2&3 ? $isSameFile23");
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}