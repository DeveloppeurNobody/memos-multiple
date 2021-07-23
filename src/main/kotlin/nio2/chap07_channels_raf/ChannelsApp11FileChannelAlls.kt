package nio2.chap07_channels_raf

import java.io.IOException
import java.nio.ByteBuffer
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*;

object ChannelsApp11FileChannelAlls {
    fun deleteCopied(path: Path) {
        try {
            Files.deleteIfExists(path);
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val copy_from: Path = Paths.get("/home/ryu/raf/ts/2009/videos/Rafa Best Shots.mp4");
        val copy_to: Path = Paths.get("/home/ryu/Rafa_copy.mp4");

        var startTime: Long;
        var elapsedTime: Long;
        val BUFFER_SIZE_KB: Int = 4;
        val BUFFER_SIZE = BUFFER_SIZE_KB * 1024;

        deleteCopied(copy_to);

        println("*** Using FileChannel and non-direct buffer...")
        try {
            FileChannel.open(copy_from, EnumSet.of(StandardOpenOption.READ))
                .use {
                    fileChannel_from ->
                    FileChannel.open(copy_to, EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE))
                        .use {
                            fileChannel_to ->

                            startTime = System.nanoTime();

                            // allocate an non-direct ByteBuffer
                            var byteBuffer: ByteBuffer = ByteBuffer.allocate(BUFFER_SIZE);

                            while (fileChannel_from.read(byteBuffer) > 0) {
                                byteBuffer.flip();
                                fileChannel_to.write(byteBuffer);
                                byteBuffer.clear();
                            }

                            elapsedTime = System.nanoTime() - startTime;
                            println("Elapsed Time is ${elapsedTime / 1000000000.0} seconds");
                        }
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        deleteCopied(copy_to);

        // FileChannel and direct buffer
        println("*** Using FileChannel and direct buffer ...");
        try {
            FileChannel.open(copy_from, EnumSet.of(StandardOpenOption.READ))
                .use { fileChannel_from ->
                    FileChannel.open(copy_to, EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE))
                        .use {
                            fileChannel_to ->

                            startTime = System.nanoTime();

                            // Allocate an direct ByteBuffer
                            var byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);

                            // Read data from file into ByteBuffer
                            while (fileChannel_from.read(byteBuffer) > 0) {
                                // flip the buffer wich set :
                                // limit = current position and position = 0;

                                byteBuffer.flip();

                                // write data from bytebuffer to file

                                fileChannel_to.write(byteBuffer);

                                // for the next read
                                // position = 0 and limit = capacity (without erasing data)
                                byteBuffer.clear();
                            }

                            elapsedTime = System.nanoTime() - startTime;
                            println("Elapsed Time is ${elapsedTime / 1000000000.0} seconds");
                        }
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        deleteCopied(copy_to);

        // FileChannel and transferTo
        println("*** Using FileChannel.transferTo method...");
        try {
            FileChannel.open(copy_from, EnumSet.of(StandardOpenOption.READ))
                .use { fileChannel_from ->
                    FileChannel.open(copy_to, EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE))
                        .use { fileChannel_to ->
                            startTime = System.nanoTime();

                            fileChannel_from.transferTo(0L, fileChannel_from.size(), fileChannel_to);

                            elapsedTime = System.nanoTime() - startTime;
                            println("Elapsed Time is ${elapsedTime / 1000000000.0} seconds");
                        }
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        deleteCopied(copy_to);

        println("*** Using FileChannel.transferFrom method...");
        try {
            FileChannel.open(copy_from, EnumSet.of(StandardOpenOption.READ))
                .use { fileChannel_from ->
                    FileChannel.open(copy_to, EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE))
                        .use { fileChannel_to ->

                            startTime = System.nanoTime();

                            fileChannel_to.transferFrom(fileChannel_from, 0L, fileChannel_from.size());

                            elapsedTime = System.nanoTime() - startTime;
                            println("Elapsed Time is ${elapsedTime / 1000000000.0} seconds");
                        }
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        deleteCopied(copy_to);

        println("*** Using FileChannel.map method...")
        try {
            FileChannel.open(copy_from, EnumSet.of(StandardOpenOption.READ))
                .use { fileChannel_from ->
                    FileChannel.open(copy_to, EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE))
                        .use { fileChannel_to ->
                            startTime = System.nanoTime();

                            var buffer: MappedByteBuffer = fileChannel_from.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel_from.size());

                            fileChannel_to.write(buffer);
                            buffer.clear();

                            elapsedTime = System.nanoTime() - startTime;
                            println("Elapsed Time is ${elapsedTime / 1000000000.0} seconds");
                        }
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}