package nio2.chap09_async

import java.io.IOException
import java.lang.Exception
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousFileChannel
import java.nio.channels.FileLock
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*
import java.util.concurrent.Future

object AsyncApp01AsynchronousFileChannelWithFutureAndLock {
    @JvmStatic
    fun main(args: Array<String>) {
        var byteBuffer: ByteBuffer = ByteBuffer.wrap("Argentines At Home In Buenos Aires Cathedral\n The Copa Claro is the third stop of the four-tournament Latin American swing, and is contested on clay at the Buenos Aires Lawn Tennis Club, known as the Cathedral of Argentinean tennis. An Argentine has reached the final in nine of the 11 editions of the ATP World Tour 250 tournament, with champions including Guillermo Coria, Gaston Gaudio, Juan Monaco and David Nalbandian.".toByteArray());
        var path: Path = Paths.get("/home/ryu/raf/ts/2009", "CopaClaro.txt");

        try {
            AsynchronousFileChannel.open(path, StandardOpenOption.WRITE)
                .use { asynchronousFileChannel ->
                    var featureLock: Future<FileLock> = asynchronousFileChannel.lock();
                    println("Waiting the file to be locked ...");
                    var lock: FileLock = featureLock.get();
                    // or, use shortcut
                    // var lock: FileLock = asynchronousFileChannel.lock().get();

                    if (lock.isValid) {
                        var featureWrite: Future<Int> = asynchronousFileChannel.write(byteBuffer, 0);
                        println("Waiting the bytes to be written...");
                        var written: Int = featureWrite.get();

                        // shorcut
                        // written: Int = asynchronousFileChannel.write(buffer, 0).get();
                        println("I written $written bytes into ${path.fileName} locked file!");

                        lock.release();
                    }
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}