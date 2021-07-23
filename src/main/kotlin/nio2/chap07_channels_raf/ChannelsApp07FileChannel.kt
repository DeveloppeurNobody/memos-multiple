package nio2.chap07_channels_raf

import java.io.*
import java.nio.ByteBuffer
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.nio.file.*
import java.util.*

object ChannelsApp07FileChannel {

    @Throws(IOException::class)
    fun deleteCopied(path: Path) {
        try {
            Files.deleteIfExists(path);
            println("File deleted: $path");
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val copyFrom: Path = Paths.get("/home/ryu/raf/ts/2009/videos/Rafa Best Shots.mp4");
        val copyTo: Path = Paths.get("/home/ryu", "rafaBestShots.mp4");
        var startTime: Long;
        var elapsedTime: Long;
        var bufferSizeKB: Int = 4;
        var bufferSize: Int = bufferSizeKB * 1024;

        deleteCopied(copyTo);

        // FileChannel and indirect buffer
        println("*** Using FileChannel and non-direct buffer ...");
        try {
            FileChannel.open(copyFrom, EnumSet.of(StandardOpenOption.READ))
                .use { fileChannelFrom ->
                    FileChannel.open(copyTo, EnumSet.of(StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE))
                        .use { fileChannelTo ->
                            startTime = System.nanoTime();

                            // Allocate an non-direct ByteBuffer
                            var byteBuffer: ByteBuffer = ByteBuffer.allocate(bufferSize);

                            // Read data from file into ByteBuffer
                            var bytesCount: Int;
                            while (fileChannelFrom.read(byteBuffer)
                                    .also { bytesCount = fileChannelFrom.read(byteBuffer) } > 0) {
                                // flip the buffer which set the limit to current position, and position to 0
                                byteBuffer.flip();

                                // write data from ByteBuffer to file
                                fileChannelTo.write(byteBuffer);

                                // for the next read
                                byteBuffer.clear();
                            }
                            elapsedTime = System.nanoTime() - startTime;
                            println("Elapsed time is ${elapsedTime / 1000000000.0} seconds");
                        }
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // we delete for the next example
        deleteCopied(copyTo)

        // FileChannel and direct buffer
        println("*** Using FileChannel and direct buffer ...");
        try {
            FileChannel.open(copyFrom, EnumSet.of(StandardOpenOption.READ))
                .use { fileChannelFrom ->
                    FileChannel.open(copyTo, EnumSet.of(StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE))
                        .use { fileChannelTo ->
                            startTime = System.nanoTime();

                            // Allocate an direct ByteBuffer
                            var byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(bufferSize);

                            // Read data from file into ByteBuffer
                            var bytesCount: Int;
                            while (fileChannelFrom.read(byteBuffer)
                                    .also { bytesCount = fileChannelFrom.read(byteBuffer)} > 0 ) {
                                // flip the buffer which set the limit to current position, and position to 0
                                byteBuffer.flip();

                                // write data from byteBuffer to file
                                fileChannelTo.write(byteBuffer);

                                // for the next read
                                byteBuffer.clear();
                            }
                            elapsedTime = System.nanoTime() - startTime;
                            println("Elapsed Time is: ${elapsedTime / 1000000000.0} seconds");
                        }
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // delete file for the next example.
        deleteCopied(copyTo);

        // FileChannel and transferTo
        println("*** Using FileChannel.transferTo method ...");
        try {
            FileChannel.open(copyFrom, EnumSet.of(StandardOpenOption.READ))
                .use { fileChannelFrom ->
                    FileChannel.open(copyTo, EnumSet.of(StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE))
                        .use { fileChannelTo ->
                            startTime = System.nanoTime();
                            fileChannelFrom.transferTo(0L, fileChannelFrom.size(), fileChannelTo);
                            elapsedTime = System.nanoTime() - startTime;
                            println("Elapsed Time is: ${elapsedTime / 1000000000.0} seconds");
                        }
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // delete file for the next example.
        deleteCopied(copyTo);

        // FileChannel and TransferFrom
        println("*** Using FileChannel.transferTo method ...");
        try {
            FileChannel.open(copyFrom, EnumSet.of(StandardOpenOption.READ))
                .use { fileChannelFrom ->
                    FileChannel.open(copyTo, EnumSet.of(StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE))
                        .use { fileChannelTo ->
                            startTime = System.nanoTime();
                            fileChannelTo.transferFrom(fileChannelFrom,0L, fileChannelFrom.size());
                            elapsedTime = System.nanoTime() - startTime;
                            println("Elapsed Time is: ${elapsedTime / 1000000000.0} seconds");
                        }
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // delete file for the next example.
        deleteCopied(copyTo);

        println("*** Using FileChannel.map method ...");
        try {
            FileChannel.open(copyFrom, EnumSet.of(StandardOpenOption.READ))
                .use { fileChannelFrom ->
                    FileChannel.open(copyTo, EnumSet.of(StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE))
                        .use { fileChannelTo ->
                            startTime = System.nanoTime();

                            var mappedByteBuffer: MappedByteBuffer = fileChannelFrom.map(FileChannel.MapMode.READ_ONLY, 0, fileChannelFrom.size());
                            fileChannelTo.write(mappedByteBuffer);
                            mappedByteBuffer.clear();

                            elapsedTime = System.nanoTime() - startTime;
                            println("Elapsed Time is: ${elapsedTime / 1000000000.0} seconds");
                        }
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // delete file for the next example.
        deleteCopied(copyTo);

        println("*** Using buffered streams and byte array ...");
        var inFileStr = copyFrom.toFile();
        var outFileStr = copyTo.toFile();

        try {
            BufferedInputStream(FileInputStream(inFileStr))
                .use { bufferedInputStream ->
                    BufferedOutputStream(FileOutputStream(outFileStr))
                    .use { bufferedOutputStream ->
                        startTime = System.nanoTime();

                        var byteArray: ByteArray = ByteArray(bufferSize);
                        var bytesCount: Int;
                        while (bufferedInputStream.read(byteArray)
                                .also { bytesCount = bufferedInputStream.read(byteArray) } != -1) {
                            bufferedOutputStream.write(byteArray, 0, bytesCount);
                        }

                        elapsedTime = System.nanoTime() - startTime;
                        println("Elapsed Time is: ${elapsedTime / 1000000000.0} seconds");
                }
            }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // delete the file for the next example.
        deleteCopied(copyTo);

        println("*** Using un-buffered streams and byte array ...");
        try {
            FileInputStream(inFileStr)
                .use { fileInputStream ->
                    FileOutputStream(outFileStr)
                        .use { fileOutputStream ->
                            startTime = System.nanoTime();

                            var byteArray: ByteArray = ByteArray(bufferSize);
                            var bytesCount: Int;
                            while (fileInputStream.read(byteArray)
                                    .also { bytesCount = fileInputStream.read(byteArray) } != -1) {
                                fileOutputStream.write(byteArray, 0, bytesCount);
                            }
                            elapsedTime = System.nanoTime() - startTime;
                            println("Elapsed Time is: ${elapsedTime / 1000000000.0} seconds");
                        }
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        deleteCopied(copyTo);

        // Using Files.copy (Path to Path)
        println("*** Using Files.copy (Path to Path) method ...");
        try {
            startTime = System.nanoTime();
            Files.copy(copyFrom, copyTo, LinkOption.NOFOLLOW_LINKS);
            elapsedTime = System.nanoTime() - startTime;
            println("Elapsed Time is: ${elapsedTime / 1000000000.0} seconds");
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        deleteCopied(copyTo);

        // Using Files.copy (Path to OutputStream)
        println("*** Using Files.copy (Path to OutputStream) ...");
        try {
            // OutputStream os = new FileOutputStream(copy_to.toFile());
            FileOutputStream(copyTo.toFile())
                .use { fileOutputStream ->
                    startTime = System.nanoTime();
                    Files.copy(copyFrom, fileOutputStream as OutputStream);
                    elapsedTime = System.nanoTime() - startTime;
                    println("Elapsed Time is: ${elapsedTime / 1000000000.0} seconds");
                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}