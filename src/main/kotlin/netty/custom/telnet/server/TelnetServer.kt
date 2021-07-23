package netty.custom.telnet.server

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.SelfSignedCertificate

/**
 * Simplistic telnet server
 */
object TelnetServer {
    val SSL = System.getProperty("ssl") != null;
    val PORT = System.getProperty("port", if (SSL) { "8992" } else { "8023" }).toInt();

    @JvmStatic
    fun main(args: Array<String>) {
        // configure SSL.
        val sslCtx: SslContext? = if (SSL) {
            val ssc: SelfSignedCertificate = SelfSignedCertificate();
            SslContextBuilder
                .forServer(ssc.certificate(), ssc.privateKey())
                .build()
        } else {
            null;
        }

        val bossGroup: EventLoopGroup = NioEventLoopGroup(1);
        val workerGroup: EventLoopGroup = NioEventLoopGroup();

        try {
            val serverBootstrap: ServerBootstrap = ServerBootstrap();
            serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
                .handler(LoggingHandler(LogLevel.INFO))
                .childHandler(TelnetServerInitializer(sslCtx));

            serverBootstrap
                .bind(PORT)
                .sync()
                .channel()
                .closeFuture()
                .sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}