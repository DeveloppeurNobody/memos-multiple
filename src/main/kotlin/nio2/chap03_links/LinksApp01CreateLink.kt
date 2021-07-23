package nio2.chap03_links

import java.io.IOException
import java.lang.UnsupportedOperationException
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path

object LinksApp01CreateLink {
    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class,
        SecurityException::class,
        UnsupportedOperationException::class
    )
    fun main(args: Array<String>) {
        var link: Path = FileSystems.getDefault()
            .getPath("rafael.nadal.4");

        var target: Path = FileSystems.getDefault()
            .getPath("/home/ryu/raf/photos", "rafa_winner.jpg");

        try {
            Files.createLink(link, target);
            println("The link was successfully created!")
        } catch (e: Exception) {
            when(e) {
                is SecurityException -> System.err.println("Permission denied ! => $e");
                is UnsupportedOperationException -> System.err.println("An unsupported operation was detected => $e");
                is IOException -> System.err.println("An I/O error occured! => $e");
                else -> System.err.println("exception: $e");
            }
        }
    }
}