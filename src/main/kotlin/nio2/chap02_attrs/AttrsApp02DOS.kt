package nio2.chap02_attrs

import java.io.IOException
import java.nio.file.Files
import java.nio.file.LinkOption
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.DosFileAttributes

object AttrsApp02DOS {
    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class
    )
    fun main(args: Array<String>) {
        var attr: DosFileAttributes? = null;
        var path: Path = Paths.get("C:/rafaelnadal/tournaments/2009", "BNP.txt");

        try {
            attr = Files.readAttributes(path, DosFileAttributes::class.java);
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        if (attr != null) {
            with(attr) {
                println("Is read only ? $isReadOnly");
                println("Is Hidden ? $isHidden");
                println("Is archive ? $isArchive");
                println("Is system ? $isSystem");
            }
        }

        // settings the hidden attributes to true
        try {
            Files.setAttribute(path, "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // gettings the hidden attributes
        try {
            var hidden: Boolean = Files.getAttribute(path, "dos:hidden", LinkOption.NOFOLLOW_LINKS) as Boolean;
            println("Is hidden ? $hidden");
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}