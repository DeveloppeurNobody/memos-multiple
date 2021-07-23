package nio2.chap07_channels_raf

import java.io.IOException
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

object ChannelsApp01ReadableChannel {
    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class
    )
    fun main(args: Array<String>) {
        var path: Path = Paths.get("/home/ryu/raf/gslam/RolandGarros", "story.txt");

        // read a file using ReadableByteChannel
        try {
            Files.newByteChannel(path)
                .use { readableByteChannel ->
                    var buffer: ByteBuffer = ByteBuffer.allocate(12);
                    // prepare buffer for reading
                    buffer.clear();
                    var encoding: String = System.getProperty("file.encoding");

                    while (readableByteChannel.read(buffer) > 0) {
                        buffer.flip();
                        println(Charset.forName(encoding).decode(buffer));
                        buffer.clear();
                    }
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        println();
        // read a file using SeekableByteChannel
        try {
            Files.newByteChannel(path, EnumSet.of(StandardOpenOption.READ))
                .use { seekableChannel ->
                    var buffer: ByteBuffer = ByteBuffer.allocate(12);
                    var encoding: String = System.getProperty("file.encoding");
                    buffer.clear();

                    while (seekableChannel.read(buffer) > 0) {
                        buffer.flip();
                        println(Charset.forName(encoding).decode(buffer));
                    }
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}