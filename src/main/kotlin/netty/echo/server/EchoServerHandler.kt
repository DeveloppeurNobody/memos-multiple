package netty.echo.server

import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter

@ChannelHandler.Sharable
class EchoServerHandler : ChannelInboundHandlerAdapter() {
    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        println(">>> Channel Read: $msg");
        ctx?.write(msg);
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext?) {
        println(">>>>>>>>>>> CHannelReadComplete")
        ctx?.flush();
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        cause?.printStackTrace();
        ctx?.close();
    }
}