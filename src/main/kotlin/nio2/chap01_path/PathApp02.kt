package nio2.chap01_path

import java.nio.file.Path
import java.nio.file.Paths

object PathApp02 {
    @JvmStatic
    @Throws(
        Exception::class
    )
    fun main(args: Array<String>) {
        var path: Path = Paths.get("/home/ryu", "raf/ts/2009", "BNP.txt");


        with(path) {
            println("The file/directory indicated by path: $fileName");
            println("Root of this path: $root");
            println("Parent: $parent");
            println("Number of name elements is path: $nameCount");
            for(i in 0 until nameCount) {
                println("Name element: $i is ${getName(i)}");
            }
            println("Subpath (0,3): ${subpath(0, 3)}");
        }
    }
}