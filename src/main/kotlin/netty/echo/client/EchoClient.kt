package netty.echo.client

import io.netty.bootstrap.Bootstrap
import io.netty.channel.*
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.InsecureTrustManagerFactory

object EchoClient {
    val SSL = System.getProperty("ssl") != null
    val HOST = System.getProperty("host", "127.0.0.1")
    val PORT = System.getProperty("port", "8007").toInt()
    val SIZE = System.getProperty("size", "256").toInt()

    @JvmStatic
    @Throws(Exception::class)
    fun main(args: Array<String>) {
        val sslCtx: SslContext? = if (SSL) {
            SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
        } else {
            null;
        }

        // Configure the client
        var group: EventLoopGroup = NioEventLoopGroup();
        try {
            var b: Bootstrap = Bootstrap();
            b.group(group)
                .channel(NioSocketChannel::class.java)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(object: ChannelInitializer<SocketChannel>(){
                    override fun initChannel(ch: SocketChannel?) {
                        if (ch != null) {
                            val pipeline: ChannelPipeline = ch.pipeline();
                            with(pipeline) {
                                if (sslCtx != null) {
                                    addLast(sslCtx.newHandler(ch.alloc(), HOST, PORT));
                                }
                                addLast(LoggingHandler(LogLevel.DEBUG));
                                addLast(EchoClientHandler());
                            }
                        }
                    }
                });

            // start the client.
            var channelFuture: ChannelFuture = b.connect(HOST, PORT)
                .sync();

            channelFuture.channel()
                .closeFuture()
                .sync()
        } finally {

        }

    }
}