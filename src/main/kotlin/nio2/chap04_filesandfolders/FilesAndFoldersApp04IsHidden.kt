package nio2.chap04_filesandfolders

import java.io.IOException
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path

object FilesAndFoldersApp04IsHidden {
    @JvmStatic
    @Throws(
     Exception::class,
     IOException::class
    )
    fun main(args: Array<String>) {
        var path: Path = FileSystems.getDefault()
            .getPath("/home/ryu/raf/ts/2009", "MutuaMadridOpen.txt");

        // is hidden ?
        try {
            var isHidden: Boolean = Files.isHidden(path);
            println("Is hidden ? $isHidden");
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}