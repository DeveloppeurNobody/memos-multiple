package netty.discard.client

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelPipeline
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.InsecureTrustManagerFactory

/**
 * Keeps sending random data to the specified address.
 */
object DiscardClient {
    private val SSL: Boolean = System.getProperty("ssl") != null;
    val HOST: String = System.getProperty("host", "127.0.0.1");
    val PORT: Int = System.getProperty("port", "8009").toInt();
    val SIZE: Int = System.getProperty("size", "256").toInt();


    @JvmStatic
    @Throws(Exception::class)
    fun main(args: Array<String>) {
        // Configure SSL.
        val sslCtx: SslContext? = if (SSL) {
            SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build()
        }
        else {
            null;
        }

        var eventLoopGroup: EventLoopGroup = NioEventLoopGroup();
        try {
            var b: Bootstrap = Bootstrap();
            b.group(eventLoopGroup)
                .channel(NioSocketChannel::class.java)
                .handler(object: ChannelInitializer<SocketChannel>() {
                    override fun initChannel(ch: SocketChannel?) {
                        if (ch != null) {
                            var pipeline: ChannelPipeline = ch.pipeline();
                            if (sslCtx != null) {
                                pipeline.addLast(sslCtx.newHandler(ch.alloc(), HOST, PORT));
                            }
                            pipeline.addLast(LoggingHandler(LogLevel.INFO));
                            pipeline.addLast(DiscardClientHandler());
                        }
                    }
                });

            // make the connection attempt.
            var f: ChannelFuture = b.connect(HOST, PORT)
                .sync();

            // wait until the connection is closed
            f.channel()
                .closeFuture()
                .sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}