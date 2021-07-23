package nio2.chap04_filesandfolders


import java.io.File
import java.io.IOException
import java.lang.Exception
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path

object FilesAndFoldersApp22DeleteTmpFileWithOioFile  {
    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class,
        InterruptedException::class
    )
    fun main(args: Array<String>) {
        var baseDir: Path = FileSystems.getDefault()
            .getPath("/home/ryu/raf/tmp");
        var tmpFilePrefix: String = "rafa_app_22_";
        var tmpFileSufix: String = ".txt";

        try {
            val tmpFile: Path = Files.createTempFile(baseDir, tmpFilePrefix, tmpFileSufix);

            var asFile: File = tmpFile.toFile();
            asFile.deleteOnExit();

            // simulate some operations with temp file until delete it
            Thread.sleep(10000);
            println("operations done!")
        } catch (e: Exception) {
            when (e) {
                is InterruptedException -> { System.err.println("InterruptedException: $e"); }
                is IOException -> { System.err.println("I/OException: $e"); }
                else -> { System.err.println("Exception: $e") }
            }
        }
    }
}