package nio2.chap07_channels_raf

import java.io.IOException
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*


object ChannelsApp10FileChannelWithFileLockTester {
    @JvmStatic
    fun main(args: Array<String>) {
        var path: Path = Paths.get("/home/ryu/raf/email", "vamos.txt");
        var byteBuffer: ByteBuffer = ByteBuffer.wrap("Trying to acccess to lock file hi hi !\n".toByteArray());

        try {
            FileChannel.open(path, EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE))
                .use { fileChannel ->

                    // we lock file for testing ChannelsApp09
                    val lock = fileChannel.lock()

                    if (lock.isValid) {
                        println("Writing to a locked file ...")
                        try {
                            // we simulate some work, so the apps does not terminate directly
                            Thread.sleep(60000)
                        } catch (ie: InterruptedException) {
                            System.err.println(ie)
                        }
                    }

                    fileChannel.position(0);
                    fileChannel.write(byteBuffer);
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}