package nio2.chap09_async

import java.io.IOException
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousFileChannel
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

object AsyncApp05AsynchronousFileChannelFutureReadingWithTimeout {
    @JvmStatic
    fun main(args: Array<String>) {
        var buffer: ByteBuffer = ByteBuffer.allocate(100);
        var bytesRead: Int = 0;
        var result: Future<Int>? = null;
        var path: Path = Paths.get("/home/ryu/raf/gslam/RolandGarros", "story.txt");

        try {
            AsynchronousFileChannel.open(path, StandardOpenOption.READ)
                .use { asynchronousFileChannel ->
                    result = asynchronousFileChannel.read(buffer, 0);
                    bytesRead = result?.get(1, TimeUnit.NANOSECONDS)!!;


                    if (result!!.isDone) {
                        println("The result is available!");
                        println("Read bytes: $bytesRead");
                    }
                }
        } catch (e: Exception) {
            when (e) {
                is TimeoutException -> {
                    if (result != null) {
                        result!!.cancel(true);
                    }
                    println("The result is not available!");
                    println("THe read task was cancelled ? ${result?.isCancelled}");
                    println("Read bytes: $bytesRead");
                }
                else -> {
                    println(e);
                }
            }
        }
    }
}