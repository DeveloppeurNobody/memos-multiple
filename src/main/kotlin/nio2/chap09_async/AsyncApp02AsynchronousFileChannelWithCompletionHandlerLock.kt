package nio2.chap09_async

import java.io.IOException
import java.lang.Exception
import java.nio.channels.AsynchronousFileChannel
import java.nio.channels.CompletionHandler
import java.nio.channels.FileLock
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

object AsyncApp02AsynchronousFileChannelWithCompletionHandlerLock {
    lateinit var current: Thread;
    var path: Path = Paths.get("/home/ryu/raf/ts/2009", "CopaClaro.txt");

    @JvmStatic
    fun main(args: Array<String>) {
        try {
            AsynchronousFileChannel.open(path, StandardOpenOption.READ, StandardOpenOption.WRITE)
                .use { asynchronousFileChannel ->
                    current = Thread.currentThread();
                    asynchronousFileChannel.lock("Lock operation status: ", object: CompletionHandler<FileLock, Any>{
                        override fun completed(result: FileLock?, attachment: Any?) {
                            println("$attachment ${result?.isValid}");

                            if (result != null && result.isValid) {
                                println("Processing the locked file...");
                                //...
                                try {
                                    result.release();
                                } catch (ioe: IOException) {
                                    System.err.println(ioe);
                                }
                            }
                            current.interrupt();
                        }

                        override fun failed(exc: Throwable?, attachment: Any?) {
                            println(attachment);
                            println("Error: $exc");
                            current.interrupt();
                        }
                    });

                    println("Waiting for file to be locked and process...");
                    try {

                    } catch (ie: InterruptedException) {
                        System.err.println(ie);
                    }
                    println("\n\nClosing everything and leave! Bye, Bye...");
                }

        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}