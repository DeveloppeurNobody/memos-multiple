package netty.echo.server

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.ssl.SslContext


class EchoServerInitialilizer(private val sslCtx: SslContext?) : ChannelInitializer<SocketChannel>() {
    override fun initChannel(ch: SocketChannel?) {
        if (ch != null) {
            val pipeline = ch.pipeline();
            with(pipeline) {
                if (sslCtx != null) {
                    addLast(sslCtx.newHandler(ch.alloc()));
                }
                addLast(EchoServerHandler());
            }
        }
    }
}