package nio2.chap03_links

import java.io.IOException
import java.lang.UnsupportedOperationException
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.LinkOption
import java.nio.file.Path
import java.nio.file.attribute.*

object LinksApp02DefaultAttributes {
    @JvmStatic
    @Throws(
        java.lang.Exception::class,
        IOException::class,
        UnsupportedOperationException::class,
        SecurityException::class
    )
    fun main(args: Array<String>) {
        // create a symbolic link with the default attributes
        var link01: Path = FileSystems.getDefault()
            .getPath(System.getProperty("user.home"), "rafael.nadal.1");

        var target01: Path = FileSystems.getDefault()
            .getPath("/home/ryu/raf/photos", "rafa_winner.jpg");

        try {
            Files.createSymbolicLink(link01, target01);
        } catch (e: java.lang.Exception) {
            when(e) {
                is SecurityException -> System.err.println("Permission denied! | e: $e");
                is UnsupportedOperationException -> System.err.println("An unsupported operation was detected! | e: $e");
                is IOException -> System.err.println("An I/O error occured | e: $e");
                else -> System.err.println("Exception: $e")
            }
        }

        // create a symbolic link with permissions.
        var link02: Path = FileSystems.getDefault()
            .getPath(System.getProperty("user.home"), "rafael.nadal.2");
        var target02: Path = FileSystems.getDefault()
            .getPath("/home/ryu/raf/photos", "rafa_winner.jpg");

        try {
            var attrs: PosixFileAttributes = Files.readAttributes(target02, PosixFileAttributes::class.java);
            var attr: FileAttribute<Set<PosixFilePermission>> = PosixFilePermissions.asFileAttribute(attrs.permissions());

            Files.createSymbolicLink(link02, target02, attr);
        } catch (e: Exception) {
            when(e) {
                is SecurityException -> System.err.println("Permission denied! | e: $e");
                is UnsupportedOperationException -> System.err.println("An unsupported operation was detected! | e: $e");
                is IOException -> System.err.println("An I/O error occured | e: $e");
                else -> System.err.println("Exception: $e")
            }
        }

        // create a symbolic link with the same lastModifedTime ans lastAccessTime as the target
        var link03: Path = FileSystems.getDefault()
            .getPath(System.getProperty("user.home"),"rafael.nadal.3");
        var target03: Path = FileSystems.getDefault()
            .getPath("/home/ryu/raf/photos", "rafa_winner.jpg");

        try {
            Files.createSymbolicLink(link03, target03);

            var lm: FileTime = Files.getAttribute(target03, "basic:lastModifiedTime", LinkOption.NOFOLLOW_LINKS) as FileTime;
            var la: FileTime = Files.getAttribute(target03, "basic:lastAccessTime", LinkOption.NOFOLLOW_LINKS) as FileTime;
            Files.setAttribute(link03, "basic:lastModifiedTime", lm, LinkOption.NOFOLLOW_LINKS);
            Files.setAttribute(link03, "basic:lastAccessTime", la, LinkOption.NOFOLLOW_LINKS);
        } catch (e: java.lang.Exception) {
            when(e) {
                is SecurityException -> System.err.println("Permission denied! | e: $e");
                is UnsupportedOperationException -> System.err.println("An unsupported operation was detected! | e: $e");
                is IOException -> System.err.println("An I/O error occured | e: $e");
                else -> System.err.println("Exception: $e")
            }
        }

    }
}