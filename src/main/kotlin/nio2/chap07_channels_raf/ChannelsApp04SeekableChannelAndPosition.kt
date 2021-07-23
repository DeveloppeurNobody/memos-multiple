package nio2.chap07_channels_raf

import java.io.IOException
import java.nio.ByteBuffer
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

object ChannelsApp04SeekableChannelAndPosition {
    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class,
        SecurityException::class
    )
    fun main(args: Array<String>) {
        var path: Path = Paths.get("/home/ryu/raf/ts/2009", "MovistarOpen.txt");
        var buffer01: ByteBuffer = ByteBuffer.wrap("Great players participate in our tournament, like: Tommy Robredo, Fernando Gonzalez, Jose Acasuso or Thomaz Bellucci.".toByteArray());
        var buffer02: ByteBuffer = ByteBuffer.wrap("Gonzales amigos!".toByteArray());

        try {
            Files.newByteChannel(path, EnumSet.of(StandardOpenOption.WRITE))
                .use { seekableChannel ->
                    seekableChannel.position(seekableChannel.size());

                    while (buffer01.hasRemaining()) {
                        seekableChannel.write(buffer01);
                    }

                    seekableChannel.position(301);

                    while (buffer02.hasRemaining()) {
                        seekableChannel.write(buffer02);
                    }

                    buffer01.clear();
                    buffer02.clear();
                }

        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}