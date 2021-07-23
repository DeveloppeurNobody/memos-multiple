package netty.custom.telnet.client

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import java.lang.Exception
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

class ReceiverOfFileData : ChannelInboundHandlerAdapter() {
    val pathTo: Path = Paths.get("/home/ryu", "z_client_retr_file");

    lateinit var fileChannel: FileChannel;
    private var fileIsNotComplete: Boolean = true;
    var capacity = -1;

    var fileSize: Long = 0;
    lateinit var pathFrom: Path;

    var position = 0L;


    lateinit var channelFuture: ChannelFuture;

    override fun channelActive(ctx: ChannelHandlerContext?) {
        println("[ 2 - ${this::class.java.simpleName} ] channelActive())");
        openFileChannel();
    }

    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        println("[ 2 - ${this::class.java.simpleName} ] channelRead new msg *** : $msg");
            when (msg) {
                is ByteBuf -> {
                    println("channelRead0(): $msg");
                    appendDataToFile(msg);
                }
                else -> {
                    println("msg is not ByteBuf");
                }
            }

      println("[ 2 - ${this::class.java.simpleName} ] -----------------------------------------------");
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext?) {

    }

    override fun channelInactive(ctx: ChannelHandlerContext?) {
        println("channelInactive() ****");
        fileChannel?.close();

    }



    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        println("[ 2 - ${this::class.java.simpleName} ] exception");
        println("[ 2 - ${this::class.java.simpleName} ] cause: ${cause?.printStackTrace()}");
        println("[ 2 - ${this::class.java.simpleName} ] -----------------------------------------------");
    }

    private fun openFileChannel() {
        try {
            // Separation of creation of file from fileChannel
            //Files.deleteIfExists(path);
            fileChannel = FileChannel.open(pathTo, EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.APPEND))
            fileChannel.position(position);
        } catch (e: Exception) {
            System.err.println(e);
        }
    }

    private fun appendDataToFile(msg: ByteBuf) {
        var byteBuf: ByteBuf = msg;
        var byteBuffer: ByteBuffer = byteBuf.nioBuffer();

        try {
            while (byteBuffer.hasRemaining()) {
                fileChannel.write(byteBuffer);
            }


        } catch (e: Exception) {
            System.err.println("Exception: $e")
        }
    }
}