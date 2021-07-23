package nio2.chap09_async

import java.io.IOException
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousFileChannel
import java.nio.channels.CompletionHandler
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

object AsyncApp06AsynchronousFileChannelCompletionHandlerWithInterrupt {
    lateinit var current: Thread;

    @JvmStatic
    fun main(args: Array<String>) {
        var byteBuffer: ByteBuffer = ByteBuffer.allocate(100);
        var path: Path = Paths.get("/home/ryu/raf/gslam/RolandGarros", "story.txt");

        try {
            AsynchronousFileChannel.open(path, StandardOpenOption.READ)
                .use { asynchronousFileChannel ->
                    current = Thread.currentThread();
                    asynchronousFileChannel.read(byteBuffer, 0, "Read operation status...", object: CompletionHandler<Int, Any>{
                        override fun completed(result: Int?, attachment: Any?) {
                            println(attachment);
                            print("ReadBytes: $result");
                            current.interrupt();
                        }

                        override fun failed(exc: Throwable?, attachment: Any?) {
                            println(attachment);
                            println("Error: $exc");
                            current.interrupt();
                        }
                    });

                    println("\nWaiting for reading operation to end...\n");
                    try {
                        current.join();
                    } catch (ie: InterruptedException) {

                    }

                    // now the buffer contains the read bytes
                    println("\n\nClosing everything and leave! Bye, Bye...");
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}