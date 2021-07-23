package nio2.chap07_channels_raf

import java.io.IOException
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.channels.FileLock
import java.nio.channels.OverlappingFileLockException
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

object ChannelsApp09FileChannelWithFileLock {
    @JvmStatic
    fun main(args: Array<String>) {
        var path: Path = Paths.get("/home/ryu/raf/email", "vamos.txt");
        var byteBuffer: ByteBuffer = ByteBuffer.wrap("Vamons rafa now ${Date()}".toByteArray());

        try {
            FileChannel.open(path, EnumSet.of(StandardOpenOption.READ, StandardOpenOption.WRITE))
                .use { fileChannel ->

                    // Use the file channel to create a lock on the file
                    // this method blocks until it can retrieve the lock

                    var lock: FileLock? = null;
                    /*
                    // action bloquante !!
                    lock = fileChannel.lock();
                    */

                    // Try acquiring the lock without blocking.
                    // This methods returns null or throws an exception if the file is already locked.

                    try {
                      lock = fileChannel.tryLock();
                    } catch (ofle: OverlappingFileLockException) {
                     // File is already locked in this thread
                     // or virtual machine
                      System.err.println(ofle);
                    }

                    if (lock != null && lock.isValid) {
                        println("Writing to a locked file ...");

                        try {
                            Thread.sleep(60000);
                        } catch (ie: InterruptedException) {
                            System.err.println(ie);
                        }

                        fileChannel.position(0);
                        fileChannel.write(byteBuffer);

                        try {
                            Thread.sleep(60000);
                        } catch (ie: InterruptedException) {
                            System.err.println(ie);
                        }
                    }
                    else {
                        println("lock is null or not valid (maybe the lock is already taken by another app");
                    }

                    // /!\ Release the lock /!\
                    if (lock != null) {
                        lock.release();
                        println("\nLock released!");
                    }
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}