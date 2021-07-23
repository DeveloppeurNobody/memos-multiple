package nio2.chap09_async

import java.io.IOException
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousFileChannel
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.concurrent.Future

object AsyncApp05AsynchronousFileChannelFutureWhenWriting {
    @JvmStatic
    fun main(args: Array<String>) {
        val buffer =
            ByteBuffer.wrap("The win keeps Nadal at the top of the heap in men's tennis, at least for a few more weeks. The world No2, Novak Djokovic, dumped out here in the semi-finals by a resurgent Federer, will come hard at them again at Wimbledon but there is much to come from two rivals who, for seven years, have held all pretenders at bay.".toByteArray());
        var path: Path = Paths.get("/home/ryu/raf/gslam/RolandGarros", "story.txt");
        try {
            AsynchronousFileChannel.open(path, StandardOpenOption.WRITE)
                .use { asynchronousFileChannel ->
                    var result: Future<Int> = asynchronousFileChannel.write(buffer, 100);

                    while (!result.isDone) {
                        println("Do something else while writing...");
                    }

                    println("Written done: ${result.isDone}");
                    println("Bytes written ${result.get()}");
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}