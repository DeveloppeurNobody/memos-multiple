package nio2.chap07_channels_raf

import java.io.IOException
import java.nio.ByteBuffer
import java.nio.channels.WritableByteChannel
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*


object ChannelsApp02WritableChannel {
    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class
    )
    fun main(args: Array<String>) {
        var path: Path = Paths.get("/home/ryu/raf/gslam/RolandGarros", "story.txt");

        // write a file using SeekableByteChannel
        try {
            Files.newByteChannel(path, EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING))
                .use { seekableChannel ->
                    var buffer: ByteBuffer = ByteBuffer.wrap("Rafa Nadal produced another masterclass of clay-court tennis to win his fifth French Open title ...".toByteArray());
                    var write: Int = seekableChannel.write(buffer);
                    println("Number of written bytes: $write");

                    // For reading
                    buffer.clear();
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // write a file using WritableByteChannel
        try {
            var writableByteChannel: WritableByteChannel = Files.newByteChannel(path, EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.APPEND));
            var buffer: ByteBuffer = ByteBuffer.wrap("Vamos Rafa!".toByteArray());
            var write: Int = writableByteChannel.write(buffer);
            println("Number of written bytes: $write");

            buffer.clear();

            // we don't use 'use' because we got SeekableChannel and we need WritableChannel
            writableByteChannel.close();
        } catch (ex: IOException) {
            System.err.println(ex)
        }
    }
}