package netty.factorial.client

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.InsecureTrustManagerFactory

/**
 * Sends a sequence of integers to a {@link FactorialServer} to calculate
 * the factorial of the specified integer.
 */
object FactorialClient {
    val SSL: Boolean = System.getProperty("ssl") != null;
    val HOST: String = System.getProperty("host", "127.0.0.1");
    val PORT: Int = System.getProperty("port", "8322").toInt();
    val COUNT: Int = System.getProperty("count", "1000").toInt();

    @JvmStatic
    @Throws(Exception::class)
    fun main(args: Array<String>) {
        // configure SSL.
        val sslCtx: SslContext? = if (SSL) {
            SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
        }
        else {
            null;
        }

        var eventLoopGroup: EventLoopGroup = NioEventLoopGroup();
        try {
            var b: Bootstrap = Bootstrap();
            b.group(eventLoopGroup)
                .channel(NioSocketChannel::class.java)
                .handler(FactorialClientInitializer(sslCtx));

            // make a new connection
            var f: ChannelFuture = b.connect(HOST, PORT)
                .sync();

            // get the handler instance to retrieve the answer.
            var handler: FactorialClientHandler = f.channel()
                .pipeline()
                .last() as FactorialClientHandler;

            // print out the answer.
            System.err.println("Factorial of $COUNT is ${handler.getFactorial()}");
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}