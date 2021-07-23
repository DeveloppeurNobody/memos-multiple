package nio2.chap04_filesandfolders

import java.io.IOException
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path

object FilesAndFoldersApp20TempFilesWithPrefixAndSufix {
    @JvmStatic
    @Throws(
     Exception::class,
     IOException::class
    )
    fun main(args: Array<String>) {
        var baseDir: Path = FileSystems.getDefault()
            .getPath("/home/ryu/raf/tmp");
        var tmpFilePrefix: String = "rafa_app20";
        var tmpFileSufix: String = ".txt";

        // get the default temporary folders path
        var defaultTmp: String = System.getProperty("java.io.tmpdir");
        println("java.io.tmp is: $defaultTmp \n");

        try {
            // passing null prefix/sufix
            var tmp01: Path = Files.createTempFile(null, null);
            println("\n:passing null prefix/sufix\ntmp01: $tmp01");

            // set a prefix and a sufix
            var tmp02: Path = Files.createTempFile(tmpFilePrefix, tmpFileSufix);
            println("\n:set a prefix and a sufix\ntmp02: $tmp02");

            //create a tmp file in the base dir
            var tmp03: Path = Files.createTempFile(baseDir, tmpFilePrefix, tmpFileSufix);
            println("\n:create a tmp file in the base dir\ntmp03: $tmp03");

        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}