package nio2.chap09_async

import java.io.IOException
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousFileChannel
import java.nio.charset.Charset
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*
import java.util.concurrent.*

object AsyncApp03newFixedThreadPool {
    private fun withOptions(): Set<StandardOpenOption> {
        val options = TreeSet<StandardOpenOption>();
        options.add(StandardOpenOption.READ);
        return options;
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val THREADS: Int = 5;
        val taskExecutor: ExecutorService = Executors.newFixedThreadPool(THREADS);

        var encoding: String = System.getProperty("file.encoding");
        var list: MutableList<Future<ByteBuffer>> = mutableListOf();
        var sheeps: Int = 0;

        var path: Path = Paths.get("/home/ryu/raf/gslam/RolandGarros", "story.txt");

        try {
            AsynchronousFileChannel.open(path, withOptions(), taskExecutor)
                .use { asynchronousFileChannel ->
                    for(i in 0..49) {
                        var worker: Callable<ByteBuffer> = Callable<ByteBuffer> {
                            var byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(ThreadLocalRandom.current().nextInt(100, 200));
                            asynchronousFileChannel.read(byteBuffer, ThreadLocalRandom.current().nextInt(0, 100).toLong()
                            );
                            byteBuffer;
                        }

                        var future: Future<ByteBuffer> = taskExecutor.submit(worker);
                        list.add(future);
                    }

                    taskExecutor.shutdown();

                    while (!taskExecutor.isTerminated) {
                        println("Counting sheeps while fill up some buffers! So fat I Counted:");
                    }

                    println("\nDone! Here are the buffers:\n");
                    list.forEach {future ->
                        var buffer: ByteBuffer = future.get();
                        println("\n\n${buffer}");
                        println("--------------");
                        buffer.flip();
                        println(Charset.forName(encoding).decode(buffer));
                        buffer.clear();
                    }
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}