package nio2.chap07_channels_raf

import java.io.IOException
import java.nio.ByteBuffer
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*


object ChannelsApp06SeekableByteChannelTruncate {
    @JvmStatic
    fun main(args: Array<String>) {
        val path = Paths.get("/home/ryu/raf/ts/2009", "BrasilOpen.txt")
        val buffer =
            ByteBuffer.wrap("The tournament has taken a lead in environmental conservation efforts, with highlights including the planting of 500 trees to neutralise carbon emissions and providing recyclable materials to local children for use in craft work.".toByteArray())
        try {
            Files.newByteChannel(
                path,
                EnumSet.of(StandardOpenOption.READ, StandardOpenOption.WRITE)
            ).use { seekableByteChannel ->
                seekableByteChannel.truncate(200)
                seekableByteChannel.position(seekableByteChannel.size() - 1)
                while (buffer.hasRemaining()) {
                    seekableByteChannel.write(buffer)
                }
                buffer.clear()
            }
        } catch (ex: IOException) {
            System.err.println(ex)
        }
    }
}