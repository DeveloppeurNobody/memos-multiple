package nio2.chap04_filesandfolders

import java.io.IOException
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.FileAttribute
import java.nio.file.attribute.PosixFilePermission
import java.nio.file.attribute.PosixFilePermissions

object FilesAndFoldersApp19CreationFilesWithAttrs {
    @JvmStatic
    @Throws(
     Exception::class,
     IOException::class
    )
    fun main(args: Array<String>) {
        var newFile01: Path = FileSystems.getDefault()
            .getPath(System.getProperty("user.home"), "raf/ts/2010/SonyEricssonOpen01.txt");
        var newFile02: Path = FileSystems.getDefault()
            .getPath("/home/ryu/raf/ts/2010/SonyEricssonOpen02.txt");
        var newFile03: Path = FileSystems.getDefault()
            .getPath("2010/SonyEricssonOpen03.txt");
        var newFile04: Path = FileSystems.getDefault()
            .getPath("../2010/SonyEricssonOpen04.txt");

        // create a file with default attributes
        try {
            Files.createFile(newFile01);
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // create a file with a set of specified attributes
        var perms: MutableSet<PosixFilePermission> = PosixFilePermissions.fromString("rw-------");
        var attr: FileAttribute<MutableSet<PosixFilePermission>> = PosixFilePermissions.asFileAttribute(perms);
        try {
            Files.createFile(newFile02, attr);
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}