package nio2.chap04_filesandfolders

import java.io.IOException
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


object FilesAndFoldersApp15newBufferedReader {
    @JvmStatic
    @Throws(
     Exception::class,
     IOException::class
    )
    fun main(args: Array<String>) {
        var wikiPath: Path = Paths.get("/home/ryu/raf/wiki", "wiki.txt");

        var charset: Charset = Charset.forName("UTF-8");

        try {
            Files.newBufferedReader(wikiPath, charset)
                .use { it ->
                    var line: String?;
                    while ( it.readLine().also { line = it } != null) {
                        println(line)
                    }
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}