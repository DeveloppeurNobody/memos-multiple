package netty.discard.server

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

class DiscardServerHandler : SimpleChannelInboundHandler<Any>() {


    override fun channelActive(ctx: ChannelHandlerContext?) {
        ctx?.writeAndFlush("Hi".toByteArray());
    }

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: Any?) {
        // discard
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        cause?.printStackTrace();
        ctx?.close();
    }
}