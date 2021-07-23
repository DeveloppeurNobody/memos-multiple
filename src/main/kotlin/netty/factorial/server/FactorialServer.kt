package netty.factorial.server

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.SelfSignedCertificate


/**
 * Receives a sequence of integers from a {@link FactorialClient} to calculate
 * the factorial of the specified integer.
 */
object FactorialServer {
    val SSL: Boolean = System.getProperty("ssl") != null;
    val PORT: Int = System.getProperty("port", "8322").toInt();

    @JvmStatic
    @Throws(Exception::class)
    fun main(args: Array<String>) {
        // configure SSL.
        val sslCtx: SslContext? = if (SSL) {
            var ssc: SelfSignedCertificate = SelfSignedCertificate();
            SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey())
                .build()
        }
        else {
            null;
        }

        var bossGroup: EventLoopGroup = NioEventLoopGroup(1);
        var workerGroup: EventLoopGroup = NioEventLoopGroup();
        try {
            var sb: ServerBootstrap = ServerBootstrap();
            sb.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
                .handler(LoggingHandler(LogLevel.INFO))
                .childHandler(FactorialServerInitializer(sslCtx));

            var f: ChannelFuture = sb.bind(PORT)
                .sync();

            // wait until it's close
            f.channel()
                .closeFuture()
                .sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully()
        }
    }
}