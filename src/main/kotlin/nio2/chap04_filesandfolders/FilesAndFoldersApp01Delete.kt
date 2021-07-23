package nio2.chap04_filesandfolders

import java.io.IOException
import java.lang.Exception
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path

object FilesAndFoldersApp01Delete {
    @JvmStatic
    @Throws(
     Exception::class,
     IOException::class
    )
    fun main(args: Array<String>) {
        var path: Path = FileSystems.getDefault()
            .getPath("/home/ryu/raf/photos", "rafa_1.jpg");

        // delete the file
        try {
            Files.delete(path);
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // delete if exists
        try {
            var success: Boolean = Files.deleteIfExists(path);
            println("Delete status: $success");
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}