package nio2.chap09_async

import java.io.IOException
import java.lang.Exception
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousFileChannel
import java.nio.channels.CompletionHandler
import java.nio.charset.Charset
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

object AsyncApp07AsynchronousFileChannelCompletionHandlerBufferAsAttachement {
    lateinit var current: Thread;
    var path: Path = Paths.get("/home/ryu/raf/gslam/RolandGarros", "story.txt");

    @JvmStatic
    fun main(args: Array<String>) {
        var handler: CompletionHandler<Int, ByteBuffer> = object : CompletionHandler<Int, ByteBuffer> {
            var encoding: String = System.getProperty("file.encoding");

            override fun completed(result: Int?, attachment: ByteBuffer?) {
                println("Read bytes: $result");
                attachment?.flip();
                println(Charset.forName(encoding).decode(attachment));
                attachment?.clear();
                current.interrupt();
            }

            override fun failed(exc: Throwable?, attachment: ByteBuffer?) {
                println(attachment);
                println("Error: $exc");
                current.interrupt();
            }
        }

        try {
            AsynchronousFileChannel.open(path, StandardOpenOption.READ)
                .use { asynchronousFileChannel ->

                    current = Thread.currentThread();
                    var byteBuffer: ByteBuffer = ByteBuffer.allocate(100);
                    asynchronousFileChannel.read(byteBuffer, 0, byteBuffer, handler);

                    println("\nWaiting for reading operation to end...\n");

                    try {
                        current.join();
                    } catch (e: Exception) {
                        System.err.println(e);
                    }

                    // the buffer was passed as argument
                    println("\n\nClosing everything and leave! Byte, bye...");
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}