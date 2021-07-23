package nio2.chap01_path

import java.io.File
import java.io.IOException
import java.net.URI
import java.nio.file.LinkOption
import java.nio.file.Path
import java.nio.file.Paths

object PathApp03 {
    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class
    )
    fun main(args: Array<String>) {
        var path: Path = Paths.get("/home/ryu/raf/ts/2009", "BNP.txt");

        // convert path to String.
        var pathToString: String = path.toString();
        println("Path to String: $pathToString");

        // convert path to URI (browser format)
        var pathToURI: URI = path.toUri();
        println("Path to URI: $pathToURI");

        // convert relative path to absolute path.
        var pathToAbsolutePath: Path = path.toAbsolutePath();
        println("Path to absolute path: $pathToAbsolutePath");

        // convert path to "real" path
        try {
            var pathToRealPath: Path = path.toRealPath(LinkOption.NOFOLLOW_LINKS);
            println("Path to real path: $pathToRealPath")
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // convert path to File object.
        var pathToFile: File = path.toFile();
        var fileToPath: Path = pathToFile.toPath();
        println("Path to file name: ${pathToFile.name}");
        println("Path to path: $fileToPath");
    }

}