package netty.discard.server

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelPipeline
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.SelfSignedCertificate
import java.lang.Exception

/**
 * Discards any incoming data
 */
object DiscardServer {
    val SSL: Boolean = System.getProperty("ssl") != null;
    val PORT: Int = System.getProperty("port", "8009").toInt();

    @JvmStatic
    @Throws(Exception::class)
    fun main(args: Array<String>) {
        // configure SSL.
        val sslCtx: SslContext? = if (SSL) {
            var ssc: SelfSignedCertificate = SelfSignedCertificate();
            SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey())
                .build();
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
                .childHandler(object: ChannelInitializer<SocketChannel>(){
                    override fun initChannel(ch: SocketChannel?) {
                        if (ch != null) {
                            var pipeline: ChannelPipeline = ch.pipeline();

                            with(pipeline) {
                                if (sslCtx != null) {
                                    addLast(sslCtx.newHandler(ch.alloc()));
                                }
                                addLast(DiscardServerHandler());
                            }
                        }
                    }
                });

            // bind and start to accept incoming connections.
            var f: ChannelFuture = sb.bind(PORT)
                .sync();

            // wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down the server.
            f.channel()
                .closeFuture()
                .sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}