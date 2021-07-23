package netty.custom.telnet.ftp

import java.nio.ByteBuffer
import java.nio.channels.AsynchronousFileChannel
import java.nio.channels.CompletionHandler
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.concurrent.Future
import java.util.concurrent.TimeoutException

object FileSystemService {

    @JvmStatic
    fun main(args: Array<String>) {


        var buffer: ByteBuffer = ByteBuffer.allocate(100);
        var bytesRead: Int = 0;
        var result: Future<Int>? = null;
        var path: Path = Paths.get("/home/ryu/raf", "story.txt");

        var i = 0;
        try {

            AsynchronousFileChannel.open(path, StandardOpenOption.READ)
                .use { asynchronousFileChannel ->
//                            asynchronousFileChannel.read(buffer, 0, buffer, CompletionHandler<Integer, ByteBuffer>(){
//
//                            });

                    asynchronousFileChannel.read(buffer, 0, buffer, object : CompletionHandler<Int, ByteBuffer> {
                        override fun completed(result: Int?, attachment: ByteBuffer?) {

                        }

                        override fun failed(exc: Throwable?, attachment: ByteBuffer?) {

                        }
                    });
//                    bytesRead = result?.get()!!;
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
                    println(e.printStackTrace());
                }
            }
        }
    }
}