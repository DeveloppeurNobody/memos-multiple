package nio2.chap02_attrs

import nio2.chap01_path.Constantes
import java.io.IOException
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes

object FileSize {
    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class
    )
    fun main(args: Array<String>) {

        var path: Path = Paths.get(System.getProperty("user.home"));

        try {

            val size = Files.size(path);

            val bytes = size;

            val kilobytes = (bytes / 1024);
            val megabytes = (kilobytes / 1024);
            val gigabytes = (megabytes / 1024);
            val terabytes = (gigabytes / 1024);
            val petabytes = (terabytes / 1024);
            val exabytes = (petabytes / 1024);
            val zettabytes = (exabytes / 1024);
            val yottabytes = (zettabytes / 1024);

            println(
                """
                #bytes $bytes
                #kilobytes $kilobytes
                #megabytes $megabytes
                #gigabytes $gigabytes
                #terabytes $terabytes
                #petabytes $petabytes
                #exabytes $exabytes
                #zettabytes $zettabytes
                #yottabytes $yottabytes    
            """.trimIndent()
            );

        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}