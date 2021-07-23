package nio2

import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

object MyFileWriterApp {
    var bufferSizeKB: Int = 4;
    var bufferSize: Int = bufferSizeKB * 1024;

    //val pathSource = Paths.get("/home/ryu/DossierSource/ChannelFuture.jpeg");
    val pathSource = Paths.get("/home/ryu/DossierSource/irsal-ftp-client-spring-1.0.0-SNAPSHOT.jar");

    var fileChannelSource: FileChannel? = null;

    //val pathTarget = Paths.get("/home/ryu/Desktop/DossierTarget/Channel.jpeg");
    val pathTarget = Paths.get("/home/ryu/Desktop/DossierTarget/irsal-ftp-client-spring-1.0.0-SNAPSHOT.jar");
    var fileChannelTarget: FileChannel? = null;

    @JvmStatic
    fun main(args: Array<String>) {
        Files.deleteIfExists(pathTarget);
        doOperation();
    }

    fun doOperation() {
        try {
            fileChannelSource = FileChannel.open(pathSource, EnumSet.of(StandardOpenOption.READ));
            println("#pathSource.size: ${Files.size(pathSource)}");
            fileChannelTarget = FileChannel.open(pathTarget, EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND));

            var byteBuffer = ByteBuffer.allocate(bufferSize);

            var bytesCount = 0;
            // read from source and affect result to byteBuffer
            while (fileChannelSource!!.read(byteBuffer).also { bytesCount = fileChannelSource!!.read(byteBuffer) } > 0) {

                // flip the buffer
                byteBuffer.flip();

                val byteArray = ByteArray(byteBuffer.remaining());

                // write data from ByteBuffer to byteArray
                byteBuffer.get(byteArray);

                // another buffer to avoid mutation of buffer by fileChannelTarget
                val secondBuffer = ByteBuffer.wrap(byteArray)
                fileChannelTarget!!.write(secondBuffer);


                // for the next read
                byteBuffer.clear();
            }

            println("#pathTarget.size: ${Files.size(pathTarget)}");
        } catch (ex: Exception) {
            println("doOperation() ERROR #ex: ${ex.printStackTrace()}");
        }
    }
}