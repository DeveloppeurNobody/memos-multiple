package nio2.chap02_attrs

import java.io.IOException
import java.nio.file.Files
import java.nio.file.LinkOption
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.*

object AttrsApp03Posix {
    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class
    )
    fun main(args: Array<String>) {
        var attr: PosixFileAttributes? = null;
        var path: Path = Paths.get("/home/ryu/raf/ts/2009/BNP.txt");
        var newPath: Path = Paths.get("/home/ryu/raf/ts/2009/new_BNP.txt");

        // get POSIX attributes using Files.readAttributes
        try {
            attr = Files.readAttributes(path, PosixFileAttributes::class.java);
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // get POSIX attributes using the Files.getFileAttributeView
        try {
            attr = Files.getFileAttributeView(path, PosixFileAttributeView::class.java).readAttributes();
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        if (attr != null) {
            with(attr) {
                println("File owner: ${owner().name}");
                println("File group: ${group().name}");
                println("File permission: ${permissions()}");
            }
        }

        // user of asFileAttribute
        var posixAttrs: FileAttribute<Set<PosixFilePermission>> = PosixFilePermissions.asFileAttribute(attr?.permissions());

        // creating file with newPath and posixAttrs for attributes
        try {
            Files.createFile(newPath, posixAttrs);
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // use of fromString
        var permissions: Set<PosixFilePermission> = PosixFilePermissions.fromString("rw-r--r--");
        try {
            Files.setPosixFilePermissions(newPath, permissions);
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // set the file group owner
        try {
            var group: GroupPrincipal = path.fileSystem
                .userPrincipalLookupService
                .lookupPrincipalByGroupName("monGroupe");
            Files.getFileAttributeView(path, PosixFileAttributeView::class.java)
                .setGroup(group);
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // get the file group owner
        try {
            var group: GroupPrincipal = Files.getAttribute(path, "posix:group", LinkOption.NOFOLLOW_LINKS) as GroupPrincipal;
            println("group.name: ${group.name}");
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}