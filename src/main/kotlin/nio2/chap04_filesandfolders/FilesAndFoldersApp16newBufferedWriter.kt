package nio2.chap04_filesandfolders

import java.io.IOException
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

object FilesAndFoldersApp16newBufferedWriter {
    @JvmStatic
    @Throws(
     Exception::class,
     IOException::class
    )
    fun main(args: Array<String>) {
        var wikiPath: Path = Paths.get("/home/ryu/raf/wiki", "wiki.txt");

        var charset: Charset = Charset.forName("UTF-8");
        var text: String = "\nVamos Yeahhhhhhhhhhhhhhhhhhhhhh!!!!";

        try {
            Files.newBufferedWriter(wikiPath, charset, StandardOpenOption.APPEND)
                .use { it.write(text) }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}