package nio2.chap07_channels_raf

import java.io.IOException
import java.nio.CharBuffer
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.nio.charset.CharacterCodingException
import java.nio.charset.Charset
import java.nio.charset.CharsetDecoder
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

object ChannelsApp08FileChannelMapMode {
    @JvmStatic
    fun main(args: Array<String>) {
        var path: Path = Paths.get("/home/ryu/raf/ts/2009", "BrasilOpen.txt");
        var buffer: MappedByteBuffer? = null;

        try {
            FileChannel.open(path, EnumSet.of(StandardOpenOption.READ))
                .use { fileChannel ->
                    buffer= fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        if (buffer != null) {
            try {
                var charset: Charset = Charset.defaultCharset();
                var charsetDecoder: CharsetDecoder = charset.newDecoder();
                var charBuffer: CharBuffer = charsetDecoder.decode(buffer);
                var content: String = charBuffer.toString();

                if (content.isNotEmpty()) {
                    println(content)
                }
                else {
                    println("file is empty!")
                }
            } catch (cce: CharacterCodingException) {
                System.err.println(cce);
            }
        }
    }
}