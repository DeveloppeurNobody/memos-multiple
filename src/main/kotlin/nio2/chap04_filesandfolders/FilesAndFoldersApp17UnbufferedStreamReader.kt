package nio2.chap04_filesandfolders

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object FilesAndFoldersApp17UnbufferedStreamReader {
    @JvmStatic
    @Throws(
     Exception::class,
     IOException::class
    )
    fun main(args: Array<String>) {
        var rnRaquet: Path = Paths.get("/home/ryu/raf/equipment", "racquet.txt");

        // using NIO.2 unbufferedStream
        var n: Int;
        try {
            println(":UnbufferedStream")
            println("-----------------")
            Files.newInputStream(rnRaquet)
                .use { inputStream ->
                    while (inputStream.read().also { n = inputStream.read() } != -1) {
                        println(n.toChar())
                    }
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        println()
        println(":BufferedStream")
        println("---------------")
        var inBuffer: ByteArray = ByteArray(1024);
        try {
            Files.newInputStream(rnRaquet)
                .use { inputStream ->
                    while (inputStream.read(inBuffer).also { n = inputStream.read(inBuffer) } != -1) {
                        println(String(inBuffer));
                    }
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // convert unbuffered stream to buffered stream by using java.io API
        println()
        println(":convert unbuffered stream to buffered stream")
        println("---------------------------------------------")
        try {
            Files.newInputStream(rnRaquet)
                .use { inputStream ->
                    BufferedReader(InputStreamReader(inputStream)).use { bufferedReader ->
                        var line: String? = null;
                        while (bufferedReader.readLine().also { line = bufferedReader.readLine() } != null) {
                            println(line);
                        }
                    }
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }


    }
}