package nio2.chap04_filesandfolders

import java.io.BufferedWriter
import java.io.IOException
import java.io.OutputStreamWriter
import java.lang.Exception
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

object FilesAndFoldersApp24TmpFileWithBuffer {
    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class,
        InterruptedException::class
    )
    fun main(args: Array<String>) {
        var baseDir: Path = FileSystems.getDefault()
            .getPath("/home/ryu/raf/tmp");
        var tmpFilePrefix: String = "rafa_app24__";
        var tmpFileSufix: String = ".txt";
        var tmpFile: Path? = null;

        // solution 1
        println(":solution 1")

        try {
            tmpFile = Files.createTempFile(baseDir, tmpFilePrefix, tmpFileSufix);
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        try {
            Files.newOutputStream(tmpFile, StandardOpenOption.DELETE_ON_CLOSE)
                .use { outputStream ->
                    BufferedWriter(OutputStreamWriter(outputStream)).use { buffereWriter ->
                        //simulate some operations sith temp file until delete it
                        Thread.sleep(10000);
                        println("operations solution 1 done!")
                    }
                }
        } catch (e: Exception) {
            when (e) {
                is InterruptedException -> { System.err.println("InterruptedException: $e"); }
                is IOException -> { System.err.println("I/OException: $e"); }
                else -> { System.err.println("Exception: $e") }
            }
        }

        // solution 2
        println(":solution 2")

        tmpFile = FileSystems.getDefault()
            .getPath("/home/ryu/raf/tmp", "${tmpFilePrefix}temporary${tmpFileSufix})");

        try {
            Files.newOutputStream(tmpFile, StandardOpenOption.CREATE, StandardOpenOption.DELETE_ON_CLOSE)
                .use { outputStream ->
                    BufferedWriter(OutputStreamWriter(outputStream)).use { buffereWriter ->
                        //simulate some operations sith temp file until delete it
                        Thread.sleep(10000);
                        println("operations solution 2 done!")
                    }
                }
        } catch (e: Exception) {
            when (e) {
                is InterruptedException -> { System.err.println("InterruptedException: $e"); }
                is IOException -> { System.err.println("I/OException: $e"); }
                else -> { System.err.println("Exception: $e") }
            }
        }


    }
}