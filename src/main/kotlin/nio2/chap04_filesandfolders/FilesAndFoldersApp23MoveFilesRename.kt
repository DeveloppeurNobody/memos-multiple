package nio2.chap04_filesandfolders

import java.io.IOException
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

object FilesAndFoldersApp23MoveFilesRename {
    @JvmStatic
    @Throws(
     Exception::class,
     IOException::class
    )
    fun main(args: Array<String>) {


        var moveFrom: Path = FileSystems.getDefault()
            .getPath("/home/ryu/raf/rafa_winner_2.jpg");
        var target: Path = FileSystems.getDefault()
            .getPath("/home/ryu/raf/rafa_winner_2.jpg");
        var targetDir: Path = FileSystems.getDefault()
            .getPath("/home/ryu/raf/photos");

        // method 1
        println(":method 1");
        try {
            Files.move(moveFrom, target, StandardCopyOption.REPLACE_EXISTING);

            println("method 1 done!")
        } catch (ioe: IOException) {
            System.err.println("ioexception - method 1: $ioe");
        }

        // method 2 - using resolve
        println(":method 2 - using resolve");
        try {
            Files.move(moveFrom, targetDir.resolve(moveFrom.fileName), StandardCopyOption.REPLACE_EXISTING);
            println("method 2 done!")
        } catch (ioe: IOException) {
            System.err.println("ioexception - method 2: $ioe");
        }

        // method 3 - using resolve to move and rename
        println(":method 3 - using resolve to move and rename")
        try {
            Files.move(target, target.resolveSibling("rafa_app_23_renamed.jpg"), StandardCopyOption.REPLACE_EXISTING);

            println("method 3 done!")
        } catch (ioe: IOException) {
            System.err.println("ioexception - method 3: $ioe");
        }
    }
}