package nio2.chap01_path

import java.io.IOException
import java.nio.file.Path
import java.nio.file.Paths

object PathApp05 {
    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class
    )
    fun main(args: Array<String>) {
        var path01: Path = Paths.get("BNP.txt");
        var path02: Path = Paths.get("AEGON.txt");
        var path03: Path = Paths.get("/ts/2009/BNP.txt");
        var path04: Path = Paths.get("/ts/2011");


        var path01ToPath02 = path01.relativize(path02);
        println(path01ToPath02);

        var path02ToPath01 = path02.relativize(path01);
        println(path02ToPath01);

        var path03ToPath04 = path03.relativize(path04);
        println(path03ToPath04);

        var path04ToPath03 = path04.relativize(path03);
//        println(path04ToPath03);


    }
}