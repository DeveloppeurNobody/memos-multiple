package nio2.chap07_channels_raf

import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.charset.Charset
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

object ChannelsApp00_FileChannel_ToByteArray {

    var fileChannelTo: FileChannel? = null;
    var fileChannelFrom: FileChannel? = null;


    @JvmStatic
    fun main(args: Array<String>) {
        println("[ ${this::class.java.simpleName} ] main()");
        val copyFrom: Path = Paths.get("/home/ryu","story_retr.txt");
       // val copyTo: Path = Paths.get("/home/ryu", "wiki_updated_copie.txt");

        var startTime: Long;
        var elapsedTime: Long;

        var bufferSizeKB: Int = 4;
        var bufferSize: Int = bufferSizeKB * 1024;

        var fileChannelTo: FileChannel? = null;
        var fileChannelFrom: FileChannel? = null;


        try {

            //============ WRITING BYTE ARRAY VIA FILE CHANNEL =======================================
//            val msg = "hello tout le monde".toByteArray(Charset.defaultCharset())
//            val buffer01: ByteBuffer = ByteBuffer.wrap(msg as ByteArray);
//            fileChannelTo = openFileChannelForWriting(copyTo);
//            while (buffer01.hasRemaining()) {
//                fileChannelTo.write(buffer01);
//            }
            //=======================================================================================


            //============ READING WITH FILE CHANNEL TO BYTE ARRAY =====================================
            val byteBuffer = ByteBuffer.allocate(bufferSize);
            fileChannelFrom = openFileChannelForReading(copyFrom);

            // Read data from file into ByteBuffer
            var bytesCount: Int;
            while (fileChannelFrom.read(byteBuffer).also { bytesCount = fileChannelFrom.read(byteBuffer) } > 0) {
                println("---------------------- FLIP------------------------- $bytesCount");

                // flip the buffer which set the limit to current position, and position to 0
                byteBuffer.flip();

                val byteArray = ByteArray(byteBuffer.remaining());

                // write data from ByteBuffer to byteArray
                byteBuffer.get(byteArray);

                // get string from bytes
                val message = String(byteArray);

                print("$message ");

                // for the next read
                byteBuffer.clear();
            }
            //=======================================================================================
            println("***********************************************************************************");
            closeChannels();

        } catch (ex: Exception) {
            println("exception: $ex");
            closeChannels();
        }

    }



    @Throws(Exception::class)
    fun closeChannels() {
       try {
           fileChannelFrom?.close();
           fileChannelTo?.close()
       } catch (ex: Exception) {
           println("closeChannel()#failed ex: ${ex.printStackTrace()}")
       }
    }

    @Throws(Exception::class)
    fun openFileChannelForWriting(path: Path): FileChannel {
        try {
            // attempt to open
            return FileChannel.open(path, EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.APPEND));
        } catch (ex: Exception) {
            throw ex;
        }
    }

    @Throws(Exception::class)
    fun openFileChannelForReading(path: Path): FileChannel {
        try {
            // attempt to open
            return FileChannel.open(path, EnumSet.of(StandardOpenOption.READ));
        } catch (ex: Exception) {
            throw ex;
        }
    }

    @Throws(Exception::class)
    fun appendDataToFile(fileChannel: FileChannel, buffer: ByteBuffer) {
        println("[${this::class.java.simpleName} ] - appendDataToFile()");
        try {
            while (buffer.hasRemaining()) {
                fileChannel.write(buffer);
            }
        } catch (ex: Exception) {
            System.err.println("Exception: ${ex.message}")
        }
    }
}