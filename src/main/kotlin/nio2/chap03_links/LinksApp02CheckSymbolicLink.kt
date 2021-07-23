package nio2.chap03_links

import java.io.IOException
import java.lang.Exception
import java.lang.UnsupportedOperationException
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path

object LinksApp02CheckSymbolicLink {
    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class,
        UnsupportedOperationException::class,
        SecurityException::class
    )
    fun main(args: Array<String>) {
        var link: Path = FileSystems.getDefault()
             .getPath("/home/ryu","rafael.nadal.1");
        var target: Path = FileSystems.getDefault()
            .getPath("/home/ryu/raf/photos", "rafa_winner.jpg");

        try {
            Files.createSymbolicLink(link, target);
            println("creation symbolic link done!")
        } catch (e: Exception) {
            when(e) {
                is SecurityException -> System.err.println("Permission denied! | $e");
                is UnsupportedOperationException -> System.err.println("An unsupported operation was detected! | $e");
                is IOException -> System.err.println("An I/O error occured | $e");
                else -> System.err.println("Exception: $e")
            }
        }

        try {
            var linkedPath: Path = Files.readSymbolicLink(link);
            println("linkedpath of symbolic link is: $linkedPath");
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}