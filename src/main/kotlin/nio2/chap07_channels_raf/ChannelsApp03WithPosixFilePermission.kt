package nio2.chap07_channels_raf

import java.io.IOException
import java.nio.ByteBuffer
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.nio.file.attribute.FileAttribute
import java.nio.file.attribute.PosixFilePermission
import java.nio.file.attribute.PosixFilePermissions
import java.util.*

object ChannelsApp03WithPosixFilePermission {
    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class,
        SecurityException::class
    )
    fun main(args: Array<String>) {
        var path: Path = Paths.get("/home/ryu/raf/email", "email.txt");
        var buffer: ByteBuffer = ByteBuffer.wrap("Hello amigo! I want to congratulate you for your second project. Best regards\n".toByteArray());

        // create the custom permissions attribute for the email.txt file
        var perms = PosixFilePermissions.fromString("r--r-----");
        var attr = PosixFilePermissions.asFileAttribute(perms);

        // write a file using SeekableByteChannel
        try {
            Files.newByteChannel(path, EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.APPEND), attr)
                .use { seekableChannel ->
                    var write: Int = seekableChannel.write(buffer);
                    println("Number of written bytes: $write");
                }
        } catch (e: Exception) {
            when (e) {
                is SecurityException -> {
                    System.err.println("SecurityException: [ $e ]");
                }
                is IOException -> {
                    System.err.println("I/OException: [ ${e.printStackTrace()} ]");
                }
                else -> {
                    System.err.println("Exception: [ $e ]");
                }
            }
        }
        buffer.clear();
    }
}