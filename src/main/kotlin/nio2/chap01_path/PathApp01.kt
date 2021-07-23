package nio2.chap01_path

import java.lang.Exception
import java.net.URI
import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.Paths

object PathApp01 {
/*
Méthode                     => Rôle
-------                        -----
Path normalize()            => Nettoyer le chemin en supprimant les éléments « . » et « .. » qu'il contient
Path relativize(Path other) => Retourner le chemin relatif à celui fourni en paramètres
Path resolve(Path)          => Combiner deux chemins
 */
    @JvmStatic
    @Throws(
        Exception::class
    )
    fun main(args: Array<String>) {
        // define a path relative to root, C:/, D:/ etc.
        var path01: Path = Paths.get("raf/ts/2009/BNP.txt");
        var path02: Path = Paths.get("raf", "ts/2009/BNP.txt");
        var path03: Path = FileSystems.getDefault().getPath("raf/ts/2009", "BNP.txt");
        var path04: Path = FileSystems.getDefault().getPath("raf/ts/2009/BNP.txt");
        var path05: Path = Paths.get(URI.create("file:///home/ryu/raf/ts/2009/BNP.txt"));

        // define a path relative to current folder.
        var path06: Path = Paths.get("raf/ts/2009/BNP.txt")
        var path07: Path = Paths.get("raf", "ts/2009/BNP.txt");
        var path08: Path = FileSystems.getDefault().getPath("raf/ts/2009", "BNP.txt");
        var path09: Path = FileSystems.getDefault().getPath("raf/ts/2009/BNP.txt");

        // define an absolute path.
        var path10: Path = Paths.get("/home/ryu/raf/ts/2009", "BNP.txt");
        var path11: Path = Paths.get("/home/ryu", "raf/ts/2009", "BNP.txt");
        var path12: Path = Paths.get("/home", "ryu", "raf", "ts", "2009", "BNP.txt");
        var path13: Path = Paths.get("/home/ryu/raf/ts/2009/BNP.txt");
        var path14: Path = Paths.get(System.getProperty("user.home"), "Downloads", "ebook-udemy-v2.pdf");
        var path15: Path = Paths.get(URI.create("file:///home/ryu/raf/ts/2009/BNP.txt"));

        // define paths using "." and ".." notations.
        var path16: Path = FileSystems.getDefault().getPath("/home/ryu/raf/ts/./2009", "BNP.txt");
        // with normalize all dots redundant or folder redundant are removed
        var path17: Path = Paths.get("/home/ryu/raf/ts/2009/dummy/../BNP.txt").normalize()
        var path18: Path = Paths.get("/home/ryu/raf/ts/./2009/dummy/../BNP.txt").normalize();

        println("All Paths were successfully defined!")

    }
}