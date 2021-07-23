package nio2.chap02_attrs

import nio2.chap01_path.Constantes.home
import java.io.IOException
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.LinkOption
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributeView
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.attribute.FileTime

object AttrsApp01 {
    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class
    )
    fun main(args: Array<String>) {
        var attr: BasicFileAttributes? = null;
        var path: Path = Paths.get(home, "raf/ts/2009", "BNP.txt");

        // extract attributes as bulk of readAttributes.
        try {
            attr = Files.readAttributes(path, BasicFileAttributes::class.java);
            println(">>>>>> ${Paths.get("").toAbsolutePath().toString()}")
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        if(attr != null) {
            with(attr) {
                println("File size: ${size()}");
                println("File creation time: ${creationTime()}");
                println("File was last time accessed at: ${lastAccessTime()}");
                println("File was last time modified at: ${lastModifiedTime()}");

                println("Is Directory: $isDirectory");
                println("Is Regular file: $isRegularFile");
                println("Is symbolic link: $isSymbolicLink");
                println("Is other: $isOther");
            }
        }

        // extract a single attribute with getAttribute
        try {
            var size: Long = Files.getAttribute(path, "basic:size", LinkOption.NOFOLLOW_LINKS) as Long
            println("Size: $size");
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // update any or all of the file's last modified time, last access time and create time attributes.
        var time: Long = System.currentTimeMillis();
        var fileTime: FileTime = FileTime.fromMillis(time);

        try {
            Files.getFileAttributeView(path, BasicFileAttributeView::class.java)
                .setTimes(fileTime, fileTime, fileTime);
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // update the file's last modified time with the setLastModifiedTime method
        try {
            Files.setLastModifiedTime(path, fileTime);
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // update the file's last modified time with the setAttribute method.
        try {
            Files.setAttribute(path, "basic:lastModifiedTime", fileTime, LinkOption.NOFOLLOW_LINKS);
            Files.setAttribute(path, "basic:creationTime", fileTime, LinkOption.NOFOLLOW_LINKS);
            Files.setAttribute(path, "basic:lastAccessTime", fileTime, LinkOption.NOFOLLOW_LINKS);
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}