package nio2.chap09_async

import java.lang.Exception
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousFileChannel
import java.nio.charset.Charset
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.concurrent.Future

object AsyncApp04AsynchronousFileChannelFutureWhenReading {
    @JvmStatic
    fun main(args: Array<String>) {
        var buffer: ByteBuffer = ByteBuffer.allocate(100);
        var encoding: String = System.getProperty("file.encoding");

        var path: Path = Paths.get("/home/ryu/raf/gslam/RolandGarros", "story.txt");
        try {
            AsynchronousFileChannel.open(path, StandardOpenOption.READ)
                .use { asynchronousFileChannel ->
                    var result: Future<Int> = asynchronousFileChannel.read(buffer, 0);

                    while (!result.isDone) {
                        println("Do something else while reading");
                    }

                    println("Reading done: ${result.isDone}");
                    println("Bytes read: ${result.get()}");
                }
        } catch (e: Exception) {
            System.err.println(e);
        }

        buffer.flip();
        println(Charset.forName(encoding).decode(buffer));
        buffer.clear();
    }
}