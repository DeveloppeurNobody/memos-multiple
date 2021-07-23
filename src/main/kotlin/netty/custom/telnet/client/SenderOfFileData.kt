package netty.custom.telnet.client

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.ChannelProgressiveFuture
import io.netty.channel.ChannelProgressiveFutureListener
import io.netty.handler.stream.ChunkedNioFile
import java.nio.channels.FileChannel
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

class SenderOfFileData : ChannelInboundHandlerAdapter() {
    var currentCtx: ChannelHandlerContext? = null;
    var pathFrom: Path? = Paths.get("/home/ryu/story2.txt");
   // var pathFrom: Path? = Paths.get("/home/ryu", "backend-my-boutique-1.0.2-SNAPSHOT.jar");
    var fileChannelFrom: FileChannel? = null;




    override fun channelActive(ctx: ChannelHandlerContext?) {
        println("[ ${this::class.java.simpleName} ] channelActive()");
        // update ctx of service to stop channel when needed;


        println("[ ${this::class.java.simpleName} ] channelActive() => attempt to openChannel!")
        openFileChannel(pathFrom!!);

        println("[ ${this::class.java.simpleName} ] channelActive() ==> attempt to transfer chunks");
        transferChunks(ctx!!)
    }

//    override fun handlerAdded(ctx: ChannelHandlerContext?) {
//        println("[ ${this::class.java.simpleName} ] handlerAdded()");
//        currentCtx = ctx;
//
//
//        println("attempt to openChannel!")
//        openFileChannel(pathFrom!!);
//
//        println("attempt to transfer chunks");
//        transferChunks(ctx!!)
//
//    }


    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        println("[ ${this::class.java.simpleName} ] channelRead()");

    }

    override fun channelReadComplete(ctx: ChannelHandlerContext?) {
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
       ctx
            ?.channel()
            ?.newFailedFuture(cause);

        cause?.printStackTrace();
    }

    override fun channelInactive(ctx: ChannelHandlerContext?) {
        println("[${this::class.java.simpleName} ] - channelInactive()");
        // update flag of client

    }

    @Throws(Exception::class)
    private fun openFileChannel(path: Path) {
        try {
            fileChannelFrom = FileChannel.open(path, EnumSet.of(StandardOpenOption.READ));
        } catch (ex: Exception) {
            println("ex: ${ex.printStackTrace()}");
        }
    }

    @Throws(Exception::class)
    private fun transferChunks(ctx: ChannelHandlerContext) {
        var valueTransfer = 0L;
        val chunkedNioFile = ChunkedNioFile(fileChannelFrom);

        // Write the content.
        val sendFileChannelFuture = ctx.writeAndFlush(chunkedNioFile, ctx.newProgressivePromise());

        sendFileChannelFuture.addListener(object : ChannelProgressiveFutureListener {

            override fun operationProgressed(future: ChannelProgressiveFuture?, progress: Long, total: Long) {
                if (total < 0) { // total unknown
                    println("${future?.channel()} Transfer progress: $progress")
                } else {
                    valueTransfer = (progress * 100) / total;
                    println("Transfer progress: $progress / $total | $valueTransfer %");
                }
            }

            override fun operationComplete(future: ChannelProgressiveFuture?) {
                println("[ ChannelProgressiveFutureListener ] //// transferChunks() => operationComplete => closing fileChannelFrom");
                fileChannelFrom!!.close();
                ctx
                    .close()
                    .addListener {
                        println("channel close!");
                        System.exit(0);
                    }

                println("transferViaChunder().operationComplete => attempt to close channel");

            }
        });
    }
}
