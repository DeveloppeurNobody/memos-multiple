package netty.echo.client

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter

class EchoClientHandler : ChannelInboundHandlerAdapter(){
    private val byteBuf: ByteBuf = Unpooled.buffer(EchoClient.SIZE);

    override fun channelActive(ctx: ChannelHandlerContext?) {
        ctx?.writeAndFlush("Connected");
    }

    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        println("msg: $msg");
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext?) {
        println("msg complete: ok!")
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        cause?.printStackTrace();
        ctx?.close();
    }

}
