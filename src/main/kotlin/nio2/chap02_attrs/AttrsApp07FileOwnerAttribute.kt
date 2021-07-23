package nio2.chap02_attrs

import java.io.IOException
import java.nio.file.Files
import java.nio.file.LinkOption
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.FileOwnerAttributeView
import java.nio.file.attribute.UserPrincipal

object AttrsApp07FileOwnerAttribute {
    @JvmStatic
    @Throws(
     Exception::class,
     IOException::class
    )
    fun main(args: Array<String>) {
        var owner: UserPrincipal? = null;
        var path: Path = Paths.get("/home/ryu/raf/ts/2009", "BNP.txt");

        // set owner using files.setOwner
        try {
            owner = path.fileSystem
                .userPrincipalLookupService
                .lookupPrincipalByName("ryu");
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // set owner using FileOwnerAttributeView.setOwner
        var fileOwnerAttributeView: FileOwnerAttributeView = Files.getFileAttributeView(path, FileOwnerAttributeView::class.java);
        try {
            fileOwnerAttributeView.owner = owner;
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // set owner using Files.setAttribute
        try {
            Files.setAttribute(path, "owner:owner", owner, LinkOption.NOFOLLOW_LINKS);
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // get owner with FileOwnerAttributeView.getOwner
        try {
            var getOwner01: String = fileOwnerAttributeView.name();
            println(getOwner01);
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // get owner with Files.getAttribute
        try {
            var getOwner02: UserPrincipal = Files.getAttribute(path, "owner:owner", LinkOption.NOFOLLOW_LINKS) as UserPrincipal;
            println(getOwner02);
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}