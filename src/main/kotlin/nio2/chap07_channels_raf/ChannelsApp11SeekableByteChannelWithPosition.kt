package nio2.chap07_channels_raf

import java.io.IOException
import java.nio.ByteBuffer
import java.nio.channels.SeekableByteChannel
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

object ChannelsApp11SeekableByteChannelWithPosition {
    @JvmStatic
    fun main(args: Array<String>) {
        var path: Path = Paths.get("/home/ryu/raf/ts/2009", "MovistarOpen.txt");
        var buffer: ByteBuffer = ByteBuffer.allocate(1);
        var encoding: String = System.getProperty("file.encoding");

        try {
            var seekableByteChannel: SeekableByteChannel = Files.newByteChannel(path, EnumSet.of(StandardOpenOption.READ));

            // set the initial position should e 0 anyway
            seekableByteChannel.position(0);
            println("reading one character from position ${seekableByteChannel.position()}");
            seekableByteChannel.read(buffer);

            /////// FLIP ////////
            buffer.flip();
            /////////////////////

            println(Charset.forName(encoding).decode(buffer));

            //----- REWIND ------
            buffer.rewind();
            //-------------------

            // get into the middle
            seekableByteChannel.position(seekableByteChannel.size() / 2);

            println("\nReading one character from position: ${seekableByteChannel.position()}");
            seekableByteChannel.read(buffer);

            /////// FLIP ////////
            buffer.flip();
            /////////////////////

            println(Charset.forName(encoding).decode(buffer));

            //----- REWIND ------
            buffer.rewind();
            //-------------------

            // get to the end
            seekableByteChannel.position(seekableByteChannel.size() - 1);

            println("\nReading one character from position: ${seekableByteChannel.position()}");
            seekableByteChannel.read(buffer);

            /////// FLIP ////////
            buffer.flip();
            /////////////////////

            println(Charset.forName(encoding).decode(buffer));

            //****** CLEAR ******
            buffer.clear();
            //*******************
            seekableByteChannel.close();
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}