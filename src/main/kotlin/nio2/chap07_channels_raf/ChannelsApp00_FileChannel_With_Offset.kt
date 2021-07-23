package nio2.chap07_channels_raf

import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.charset.Charset
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

object ChannelsApp00_FileChannel_With_Offset {

    val copyFrom: Path = Paths.get("/home/ryu","story_retr.txt");
    val copyTo: Path = Paths.get("/home/ryu", "story_retr_copie.txt");

    var startTime: Long = 0L;
    var elapsedTime: Long = 0L;

    var bufferSizeKB: Int = 4;
    var bufferSize: Int = bufferSizeKB * 1024;

    var fileChannelTo: FileChannel? = null;
    var fileChannelFrom: FileChannel? = null;



    @JvmStatic
    fun main(args: Array<String>) {
        println("[ ${this::class.java.simpleName} ] main()");
        try {

            println("[ ${this::class.java.simpleName} ] main() #writeWithCuttingPosition()");
            // write with cutting position;
            var lastPosition = writeWithCuttingPosition(32768);
            println("[ ${this::class.java.simpleName} ] main() #writeWithCuttingPosition() => lastPosition: $lastPosition");

            // close channels
            closeChannels();

            // sleep 2s
            println("[ ${this::class.java.simpleName} ] main() #writeWithCuttingPosition() sleep 2s");
            Thread.sleep(2000);

            // continue writing with last position
            println("[ ${this::class.java.simpleName} ] main() #writeWithStartingPosition() => startingPosition: $lastPosition");
            writeWithStartingPosition(lastPosition);

            // close channels
            println("[ ${this::class.java.simpleName} ] main() #closeChannels()");
            // close all file channels
            closeChannels()
        } catch (ex: Exception) {
            println("ex: ${ex.printStackTrace()}")
            closeChannels();
        }
    }


    @Throws(Exception::class)
    fun writeWithCuttingPosition(position: Long): Long {
        try {
            fileChannelFrom = openFileChannelForReading(copyFrom)
            fileChannelTo = openFileChannelForWriting(copyTo);

            // Allocate an non-direct ByteBuffer
            var byteBuffer: ByteBuffer = ByteBuffer.allocate(bufferSize);


            var currentPosition = 0L;

            // Read data from file into ByteBuffer
            var bytesCount: Int;
            while (fileChannelFrom!!.read(byteBuffer)
                    .also { bytesCount = fileChannelFrom!!.read(byteBuffer) } > 0) {

                // flip the buffer which set the limit to current position, and position to 0
                byteBuffer.flip();

                print("${fileChannelFrom!!.position()} ");

                // write data from ByteBuffer to file
                fileChannelTo!!.write(byteBuffer);

                // for the next read
                byteBuffer.clear();

                if (fileChannelFrom!!.position() > 90112L) {
                    currentPosition = fileChannelFrom!!.position();
                    break;
                }
            }

            elapsedTime = System.nanoTime() - startTime;
            println("\n[ ${this::class.java.simpleName} ] - writeWithCuttingPosition() #Elapsed time is ${elapsedTime / 1000000000.0} seconds");

            return currentPosition;
        } catch (ex: Exception) {
            throw ex;
        }
    }

    @Throws(Exception::class)
    fun writeWithStartingPosition(position: Long): Long {
        try {
            fileChannelFrom = openFileChannelForReading(copyFrom)
            fileChannelTo = openFileChannelForWriting(copyTo);

            // update position for reading
            fileChannelFrom!!.position(position);

            // Allocate an non-direct ByteBuffer
            var byteBuffer: ByteBuffer = ByteBuffer.allocate(bufferSize);

            // Read data from file into ByteBuffer
            var bytesCount: Int;
            while (fileChannelFrom!!.read(byteBuffer)
                    .also { bytesCount = fileChannelFrom!!.read(byteBuffer) } > 0) {

                // flip the buffer which set the limit to current position, and position to 0
                byteBuffer.flip();

                // write data from ByteBuffer to file
                fileChannelTo!!.write(byteBuffer);

                // for the next read
                byteBuffer.clear();
            }

            elapsedTime = System.nanoTime() - startTime;
            println("[ ${this::class.java.simpleName} ] - writeWithStartingPosition() #Elapsed time is ${elapsedTime / 1000000000.0} seconds");

            return fileChannelFrom!!.position();
        } catch (ex: Exception) {
            throw ex;
        }
    }


    @Throws(Exception::class)
    fun closeChannels() {
        try {
            ChannelsApp00_FileChannel_ToByteArray.fileChannelFrom?.close();
            ChannelsApp00_FileChannel_ToByteArray.fileChannelTo?.close()
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