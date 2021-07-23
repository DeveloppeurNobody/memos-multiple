package nio2.chap01_path

import java.io.IOException
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object PathApp06Comparaison {
    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class
    )
    fun main(args: Array<String>) {
        var path01: Path = Paths.get("/home/ryu/raf/ts/2009/BNP.txt");
        var path02: Path = Paths.get(System.getProperty("user.home"), "raf/ts/2009/BNP.txt");

        if (path01.equals(path02)) {
            println("The paths are equal!");
        }
        else {
            println("The paths are not equal!");
        }

        // compare using Files.isSameFile
        try {
            var check: Boolean = Files.isSameFile(path01, path02);
            if (check) {
                println("The paths locate the same file!");
            }
            else {
                println("The paths does not locate the same file!");
            }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // compare using Path.compareTo
        var compare: Int = path01.compareTo(path02);
        println("compare value: $compare");

        // compare using startsWith and endsWith
        var startsWith: Boolean = path01.startsWith("/raf/ts");
        var endsWith: Boolean = path02.endsWith("BNP.txt");
        println("startsWith: $startsWith");
        println("endsWith: $endsWith");
    }
}