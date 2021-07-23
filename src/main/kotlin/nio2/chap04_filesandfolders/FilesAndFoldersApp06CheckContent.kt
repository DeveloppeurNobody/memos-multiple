package nio2.chap04_filesandfolders

import java.io.IOException
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object FilesAndFoldersApp06CheckContent {
    @JvmStatic
    @Throws(
     Exception::class,
     IOException::class
    )
    fun main(args: Array<String>) {
        var emailPath: Path = Paths.get("/home/ryu/raf/email", "email.txt");
        var ballPath: Path = Paths.get("/home/ryu/raf/photos", "baseball.png");


        // readAllBytes
        try {
            var emailArray: ByteArray = Files.readAllBytes(emailPath);

            // --- CHECK THE BYTE ARRAY CONTENT ---
            var emailString: String = String(emailArray, Charset.forName("ISO-8859-1"));
            println("checking emailString content...")
            println(emailString);
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        try {
            var ballArray: ByteArray = Files.readAllBytes(ballPath);

            // --- CHECK THE ARRAY CONTENT ---
            Files.write(ballPath.resolveSibling("bytes_to_ball.png"), ballArray);
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // readAllLines
        var charset: Charset = Charset.forName("ISO-8859-1");
        try {
            var lines: MutableList<String> = Files.readAllLines(emailPath, charset);
            // Check the list content
            println("Checking lines list...")
            lines.forEach(::println);
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}