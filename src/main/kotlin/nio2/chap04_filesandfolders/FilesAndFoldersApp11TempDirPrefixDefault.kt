package nio2.chap04_filesandfolders

import java.io.IOException
import java.lang.Exception
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path

object FilesAndFoldersApp11TempDirPrefixDefault {
    @JvmStatic
    @Throws(
     Exception::class,
     IOException::class
    )
    fun main(args: Array<String>) {
        var baseDir: Path = FileSystems.getDefault()
            .getPath("/home/ryu/raf/tmp");
        var tmpDirPrefix: String = "rafa_";

        // get the default temporary folders path
        var defaultTmp: String = System.getProperty("java.io.tmpdir");
        println("defaultTmp is: $defaultTmp");

        try {
            // passing null prefix
            var tmp01: Path = Files.createTempDirectory(null);
            println("tmp01 with prefix null: $tmp01");

            // set a prefix
            var tmp02: Path = Files.createTempDirectory(tmpDirPrefix);
            println("tmp02 with prefix: $tmp02");

            // create a tmp directory in the base dir
            var tmp03: Path = Files.createTempDirectory(baseDir, tmpDirPrefix);
            println("tmp03: $tmp03");
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}