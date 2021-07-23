package netty.echo.server

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.SelfSignedCertificate

object EchoServer {
    val SSL: Boolean = System.getProperty("ssl") != null;
    val PORT: Int = System.getProperty("port", "8007").toInt();

    @JvmStatic
    @Throws(Exception::class)
    fun main(args: Array<String>) {
        // configure SSL.
        val sslCtx = if (SSL) {
            var ssc: SelfSignedCertificate = SelfSignedCertificate();
            SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey())
                .build();
        } else {
            null;
        }

        // configure the server
        var bossGroup: EventLoopGroup = NioEventLoopGroup(1);
        var workerGroup: EventLoopGroup = NioEventLoopGroup();

        try {
            var b: ServerBootstrap = ServerBootstrap();
            b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
                .option(ChannelOption.SO_BACKLOG, 100)
                .handler(LoggingHandler(LogLevel.INFO))
                .childHandler(EchoServerInitialilizer(sslCtx));

            // start the server
            var future: ChannelFuture = b.bind(PORT).sync();

            // wait until the server socket is closed;
            future.channel()
                .closeFuture()
                .sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}