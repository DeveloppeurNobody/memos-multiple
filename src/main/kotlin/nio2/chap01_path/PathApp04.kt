package nio2.chap01_path

import java.io.IOException
import java.nio.file.Path
import java.nio.file.Paths

object PathApp04 {
    
    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class
    )
    fun main(args: Array<String>) {
        // define the fix path.
        var base01: Path = Paths.get("/home/ryu/raf/ts/2009");
        var base02: Path = Paths.get("/home/ryu/raf/ts/2009/BNP.txt");

        // resolve BNP.txt file
        var path01: Path = base01.resolve("BNP.txt");
        println("Path01: $path01");

        // resolve AEGON.txt file
        var path02: Path = base01.resolve("AEGON.txt");
        println("Path02: $path02");

        // resolve sibling AEGON.file
        var path03: Path = base02.resolveSibling("AEGON.txt");
        println("Path03: $path03");
    }
}