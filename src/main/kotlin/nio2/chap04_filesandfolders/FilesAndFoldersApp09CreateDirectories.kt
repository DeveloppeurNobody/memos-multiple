package nio2.chap04_filesandfolders

import java.io.IOException
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.FileAttribute
import java.nio.file.attribute.PosixFilePermission
import java.nio.file.attribute.PosixFilePermissions

object FilesAndFoldersApp09CreateDirectories {
    @JvmStatic
    @Throws(
     Exception::class,
     IOException::class
    )
    fun main(args: Array<String>) {
        var newDir01: Path = FileSystems.getDefault()
            .getPath("/home/ryu/raf/ts/2010");
        var newDirPosix: Path = FileSystems.getDefault()
            .getPath("/home/ryu/raf/ts/2010");
        var newDir02: Path = FileSystems.getDefault()
            .getPath("/2010");
        var newDir03: Path = FileSystems.getDefault()
            .getPath("2010");
        var newDir04: Path = FileSystems.getDefault()
            .getPath("../2010");
        var newDir05: Path = FileSystems.getDefault()
            .getPath("/home/ryu/raf", "statistics/win/prizes");

        try {
            Files.createDirectory(newDir01);
            println("newDir01 done!")
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        try {
            Files.createDirectories(newDir05)
            println("newDir05 done !")
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        var perms: Set<PosixFilePermission> = PosixFilePermissions.fromString("rwxr-x---");
        var attr: FileAttribute<Set<PosixFilePermission>> = PosixFilePermissions.asFileAttribute(perms);
        try {
            Files.createDirectories(newDirPosix, attr);
            println("newDirPosix done!");
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}