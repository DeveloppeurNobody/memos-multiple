package nio2.chap07_channels_raf


import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*


object ChannelsApp07FileChannelMap {

    fun deleteCopied(path: Path) {
        try {
            Files.deleteIfExists(path);
        } catch (e: Exception) {
            System.err.println(e);
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val copy_from: Path = Paths.get("/home/ryu/raf/ts/2009/videos/Rafa Best Shots.mp4");
        val copy_to: Path = Paths.get("/home/ryu/Rafa Best Shots copy.mp4");

        var startTime: Long;
        var elapsedTime: Long;

        var bufferSizeKB: Int = 4;
        var bufferSize = bufferSizeKB * 1024;


        deleteCopied(copy_to);

        println("Using FileChannel.transferTo method ...")
        try {
            FileChannel.open(copy_from, EnumSet.of(StandardOpenOption.READ)).use { fileChannel_from ->
                FileChannel.open(copy_to, EnumSet.of(StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)).use { fileChannel_to ->

                    startTime = System.nanoTime();

                    var buffer: MappedByteBuffer = fileChannel_from.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel_from.size());

                    fileChannel_to.write(buffer);
                    buffer.clear();

                    elapsedTime = System.nanoTime() - startTime;
                    println("Elapsed Time is ${elapsedTime / 1000000000.0} seconds");
                }
            }

        } catch (e: Exception) {
            System.err.println(e);
        }

    }
}