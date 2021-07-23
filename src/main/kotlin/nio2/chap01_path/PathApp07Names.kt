package nio2.chap01_path

import java.nio.file.Paths

object PathApp07Names {

    @JvmStatic
    fun main(args: Array<String>) {
        Paths.get("/home/ryu", "raf/ts/2009", "BNP.txt")
            .forEach(::println)
    }
}