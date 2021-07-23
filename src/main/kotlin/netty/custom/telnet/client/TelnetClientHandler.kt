package netty.custom.telnet.client

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler


class TelnetClientHandler : SimpleChannelInboundHandler<String>() {

    override fun channelActive(ctx: ChannelHandlerContext?) {
        println("TelnetClientHandler.channelActive()")
    }

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: String?) {
        println("channelRead0(): $msg");
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        cause?.printStackTrace();
        ctx?.close();
    }

    override fun channelInactive(ctx: ChannelHandlerContext?) {
        println("TelnetClientHandler.channelInactive()")
    }
}