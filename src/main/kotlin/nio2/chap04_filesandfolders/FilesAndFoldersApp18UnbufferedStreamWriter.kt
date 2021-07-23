package nio2.chap04_filesandfolders

import java.io.BufferedOutputStream
import java.io.BufferedWriter
import java.io.IOException
import java.io.OutputStreamWriter
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

object FilesAndFoldersApp18UnbufferedStreamWriter {
    @JvmStatic
    @Throws(
     Exception::class,
     IOException::class
    )
    fun main(args: Array<String>) {
        var rnRaquet: Path = Paths.get("/home/ryu/raf/equipment", "racquet.txt");
        var racquet: String = "Racquet: Babolat AeroPro Drive GT XL Model\n";
        var string: String = "\nString: Babolat RPM Blast 199999999\n";

        // using NIO.2 unbuffered stream
        var data: ByteArray = racquet.toByteArray();
        try {
            Files.newOutputStream(rnRaquet)
                .use { it.write(data) }

        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // convert unbuffered stream to buffered stream by using java.io API
        try {
            Files.newOutputStream(rnRaquet, StandardOpenOption.APPEND)
                .use { outputStream ->
                    BufferedWriter(OutputStreamWriter(outputStream)).use { buffredWriter ->
                        buffredWriter.write(string);
                    }
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}