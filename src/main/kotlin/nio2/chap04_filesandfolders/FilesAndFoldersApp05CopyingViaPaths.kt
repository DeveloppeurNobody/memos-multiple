package nio2.chap04_filesandfolders

import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URI
import java.nio.file.*

object FilesAndFoldersApp05CopyingViaPaths {
    @JvmStatic
    @Throws(
     Exception::class,
     IOException::class
    )
    fun main(args: Array<String>) {

        // Path to Path copy
        var copyFrom01: Path = Paths.get("/home/ryu/raf/gslam/AustralianOpen", "draw_template.txt");
        var copyTo01: Path = Paths.get("/home/ryu/raf/gslam/USOpen", copyFrom01.fileName.toString());

        try {
            Files.copy(copyFrom01, copyTo01, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES, LinkOption.NOFOLLOW_LINKS);
            println("copying via Files.copy() done !")
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // InputStream to Path copy
        var copyFrom02: Path = Paths.get("/home/ryu/raf/gslam/AustralianOpen", "draw_template.txt");
        var copyTo02: Path = Paths.get("/home/ryu/raf/gslam/Wimbledon", "draw_template.txt");

        try {
            FileInputStream(copyFrom02.toFile())
                .use { fileInputStream ->
                    Files.copy(fileInputStream, copyTo02, StandardCopyOption.REPLACE_EXISTING);
                }
            println("copying via FileInputStream done !")
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // with URI
        var copyTo03: Path = Paths.get("/home/ryu/raf/photos/rafa_winner_2.jpg");
        var u: URI = URI.create("https://thumbor.forbes.com/thumbor/960x0/https%3A%2F%2Fspecials-images.forbesimg.com%2Fdam%2Fimageserve%2F1166022720%2F960x0.jpg%3Ffit%3Dscale");
        try {
            u.toURL()
                .openStream()
                .use { inputStream ->
                    Files.copy(inputStream, copyTo03);
                }
            println("copying via Uri done !");
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // Path to OutputStream copy
        var copyFrom04: Path = Paths.get("/home/ryu/raf/gslam/AustralianOpen", "draw_template.txt");
        var copyTo04: Path = Paths.get("/home/ryu/raf/gslam/RolandGarros", "draw_template.txt");

        try {
            FileOutputStream(copyTo04.toFile())
                .use { outputStream ->
                    Files.copy(copyFrom04, outputStream);
                }
            println("Copying via FileOutputStream done !")
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

    }
}