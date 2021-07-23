package nio2.chap04_filesandfolders

import java.io.File
import java.io.IOException
import java.lang.Exception
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path

object FilesAndFoldersApp12DeleteOnExist {
    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class,
        InterruptedException::class
    )
    fun main(args: Array<String>) {
        var baseDir: Path = FileSystems.getDefault()
            .getPath("/home/ryu/raf/tmp");
        var tmpDirPrefix: String = "rafa__";

        try {
            // create a tmp directory in the base dir
            var tmpDir: Path = Files.createTempDirectory(baseDir, tmpDirPrefix);

            var asFile: File = tmpDir.toFile();
            asFile.deleteOnExit();

            // simulate some operations with temp file until delete it
            // EACH ENTRY SHOULD BE REGISTRED FOR DELETE ON EXIT
            Thread.sleep(3000);
            println("operations done !");
        } catch (e: Exception) {
            when (e) {
                is IOException -> System.err.println("I/O exception: $e");
                is InterruptedException -> System.err.println("Interrupted Exception: $e");
                else -> System.err.println("Exception: $e");
            }
        }
    }
}