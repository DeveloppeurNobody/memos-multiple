package nio2.chap07_channels_raf

import java.io.IOException
import java.nio.ByteBuffer
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

object ChannelsApp05SeekableByteChannelAppendToEndFile {
    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class,
        SecurityException::class
    )
    fun main(args: Array<String>) {
        var path: Path = Paths.get("/home/ryu/raf/ts/2009", "SodaOpen.txt");

        var copy: ByteBuffer = ByteBuffer.allocate(25);
        copy.put("\n".toByteArray());

        try {
            Files.newByteChannel(path, EnumSet.of(StandardOpenOption.READ, StandardOpenOption.WRITE))
                .use { seekableByteChannel ->
                    var nBytes: Int;
                    do {
                        nBytes = seekableByteChannel.read(copy);
                    } while (nBytes != -1 && copy.hasRemaining())

                    copy.flip();

                    seekableByteChannel.position(seekableByteChannel.size());
                    while (copy.hasRemaining()) {
                        seekableByteChannel.write(copy);
                    }

                    copy.clear();
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}