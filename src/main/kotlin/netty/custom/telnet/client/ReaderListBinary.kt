package netty.custom.telnet.client

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import java.lang.Exception
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

class ReaderListBinary(var position: Long = 0L) : SimpleChannelInboundHandler<ByteArray>() {


    val pathTo: Path = Paths.get("/home/ryu", "z_client_retr_file");

    var fileChannelFrom: FileChannel? = null;
    private var fileIsNotComplete: Boolean = true;
    var capacity = -1;

    var fileSize: Long = 0;
    lateinit var pathFrom: Path;




    override fun channelActive(ctx: ChannelHandlerContext?) {
        println("[ 2 - ${this::class.java.simpleName} ] channelActive())");
        openFileChannel();
    }

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: ByteArray?) {
        // buffer netty -> to byteArray -> byteBuffer
        val buffer: ByteBuffer = ByteBuffer.wrap(msg as ByteArray);
        try {
            while (buffer.hasRemaining()) {
                fileChannelFrom!!.write(buffer);
            }

            // if we're here - mean ok
            true;

        } catch (ex: Exception) {
            throw ex;
        }
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext?) {

    }

    override fun channelInactive(ctx: ChannelHandlerContext?) {
        println("channelInactive() ****");
        fileChannelFrom?.close();

    }



    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        println("[ 2 - ${this::class.java.simpleName} ] exception");
        println("[ 2 - ${this::class.java.simpleName} ] cause: ${cause?.printStackTrace()}");
        println("[ 2 - ${this::class.java.simpleName} ] -----------------------------------------------");
    }

    @Throws(Exception::class)
    fun openFileChannel() {
        println("[ ${this::class.java.simpleName} ] openFileChannel() - position: $position");
        try {
            // attempt to open
            fileChannelFrom = FileChannel.open(pathTo, EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.APPEND));
            fileChannelFrom!!.position(position);
        } catch (ex: Exception) {
            throw ex;
        }
    }


}