package nio2.chap04_filesandfolders

import java.io.IOException
import java.lang.Exception
import java.nio.file.*
import java.nio.file.attribute.FileTime
import java.util.concurrent.TimeUnit

object FilesAndFoldersApp13FiltersGlob {
    @JvmStatic
    @Throws(
     Exception::class,
     IOException::class
    )
    fun main(args: Array<String>) {
        var path: Path = Paths.get("/home/ryu/raf/ts/2009");

        // no filter applyied
        println("No filter applyied")
        try {
            Files.newDirectoryStream(path)
                .use { directoryStream ->
                    directoryStream.forEach(::println);
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        println("*****")
        // glob pattern applyied
        println("Glob pattern applyied");
        try {
            Files.newDirectoryStream(path, "*{png,jpg,bmp}")
                .use { direcoryStream ->
                    direcoryStream.forEach(::println);
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        println("*****");
        var dirFilter: DirectoryStream.Filter<Path> =
            DirectoryStream.Filter<Path> { Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS) };

        // user defined filter - only files modified in the current day
        var timerFilter: DirectoryStream.Filter<Path> =
            DirectoryStream.Filter<Path> {
                var currentTime: Long = FileTime.fromMillis(System.currentTimeMillis())
                    .to(TimeUnit.DAYS);
                var modifiedTime: Long = Files.getAttribute(it, "basic:lastModifiedTime", LinkOption.NOFOLLOW_LINKS) as Long;
                if (currentTime == modifiedTime) {
                    return@Filter true
                }
                return@Filter false;
            };

        // user defined filter - only hidden files/directories
        var hiddenFilter: DirectoryStream.Filter<Path> =
            DirectoryStream.Filter<Path> {
                return@Filter Files.isHidden(it);
            }

        println("*****")
        // user defined filter applyied
        println("\nUser defined filter applyied:");
        try {
            Files.newDirectoryStream(path, dirFilter)
                .use { directoryStream ->
                    directoryStream.forEach { println(it.fileName)};
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}