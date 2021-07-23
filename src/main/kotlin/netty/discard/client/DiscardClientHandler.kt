package netty.discard.client

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

class DiscardClientHandler : SimpleChannelInboundHandler<Any>() {
    private lateinit var content: ByteBuf;
    private lateinit var ctx: ChannelHandlerContext;

    override fun channelActive(ctx: ChannelHandlerContext?) {
        if (ctx != null) {
            this.ctx = ctx;

            // Initialize the message.
            content = ctx.alloc()
                .directBuffer(DiscardClient.SIZE);

            // Send the initial messages.
            generateTraffic();
        }
    }

    override fun channelInactive(ctx: ChannelHandlerContext?) {
        content.release();
    }

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: Any?) {
        // Server is supposed to send nothing, but if it sends something, discard it.
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        // close the connection when an exception is raised.
        cause?.printStackTrace();
        ctx?.close();
    }

    private var counter: Long = 0;

    private fun generateTraffic() {
        // flush the outbound buffer to the socket.
        // once flushed, generate the same amount of traffic again.
        ctx.writeAndFlush(content.retainedDuplicate())
            .addListener(object : ChannelFutureListener {
                override fun operationComplete(future: ChannelFuture?) {
                    if (future != null) {
                        if (counter < 20) {
                            counter++;
                            if (future.isSuccess) {
                                generateTraffic();
                            } else {
                                future.cause()
                                    .printStackTrace();
                                future.channel()
                                    .close();
                            }
                        }
                        else {
                            future.channel()
                                .close();
                        }
                    }
                }
            });
    }
}