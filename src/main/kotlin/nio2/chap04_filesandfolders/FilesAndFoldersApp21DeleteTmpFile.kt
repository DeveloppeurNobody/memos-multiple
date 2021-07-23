package nio2.chap04_filesandfolders

import java.io.IOException
import java.lang.Exception
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import kotlin.concurrent.thread

object FilesAndFoldersApp21DeleteTmpFile {
    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class,
        InterruptedException::class
    )
    fun main(args: Array<String>) {
        var baseDir: Path = FileSystems.getDefault()
            .getPath("/home/ryu/raf/tmp");
        var tmpFilePrefix: String = "rafa_app21_";
        var tmpFileSufix: String = ".txt";

        try {
            var tmpFile: Path = Files.createTempFile(baseDir, tmpFilePrefix, tmpFileSufix);

            Runtime.getRuntime()
                .addShutdownHook(object: Thread(){
                    override fun run() {
                        println("Deleting the temporary file ...");
                        try {
                            Files.delete(tmpFile);
                        } catch (ioe: IOException) {
                            System.err.println(ioe);
                        }
                        println("Shutdown hook completed ...");
                    }
                });

            // simulate some operations with temp file until delete it
            Thread.sleep(3000);
            // operations done
            println("operations done !")

        } catch (e: Exception) {
            when (e) {
                is InterruptedException -> { System.err.println("InterruptedException: $e"); }
                is IOException -> { System.err.println("I/OException: $e"); }
                else -> { System.err.println("Exception: $e") }
            }
        }

    }
}