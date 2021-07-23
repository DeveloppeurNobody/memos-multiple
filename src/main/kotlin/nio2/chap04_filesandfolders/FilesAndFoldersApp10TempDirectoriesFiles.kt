package nio2.chap04_filesandfolders

import java.io.IOException
import java.lang.Exception
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path

object FilesAndFoldersApp10TempDirectoriesFiles {
    @JvmStatic
    @Throws(
     Exception::class,
     IOException::class
    )
    fun main(args: Array<String>) {
        val baseDir: Path = FileSystems.getDefault()
            .getPath("/home/ryu/raf/tmp");
        val tmpDirPrefix: String = "rafa_";

        try {
            // create a tmp directory in a base dir
            var tmpDir: Path = Files.createTempDirectory(baseDir, tmpDirPrefix);
            println("tmpDir: $tmpDir");

            println("Attempt to delete all temporaries files and directories ...")
            Runtime.getRuntime()
                .addShutdownHook(object: Thread(){
                    override fun run() {
                        println("Deleting the temporary folder ...");
                        try {
                            Files.newDirectoryStream(tmpDir)
                                .use {
                                    it.forEach(Files::delete)

                                    Files.delete(tmpDir);
                                }

                        } catch (ioe: IOException) {
                            System.err.println(ioe);
                        }
                        println("Shutdown-hook completed ...")
                    }
                });

            // simulate some operations with temp dir until delete it
            Thread.sleep(5000);
            // operations done
            println("operations done !");

        } catch (e: Exception) {
            when (e) {
                is InterruptedException -> println("Interrupted Exception: $e");
                is IOException -> println("An I/O exception: $e");
                else -> println("Exception: $e");
            }
        }
    }
}