package org.irsal.app.client.control


import org.irsal.app.client.GenerateLogger
import org.irsal.app.client.control.initializer.ControlConnectionInitializer
import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelFuture
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.InsecureTrustManagerFactory
import java.io.BufferedReader
import java.io.InputStreamReader


/**
 * Simplistic client telnet client.
 */
object ControlConnection {

    val LOG = GenerateLogger.getLoggerForClass(this);

    var isASCII = false;

    val SSL = System.getProperty("ssl") != null;
    val HOST = System.getProperty("host", "192.168.1.50");
    val PORT =
        System.getProperty("port", if (SSL) { "8992" }
        else { "8023" }).toInt();

    @JvmStatic
    fun main(args: Array<String>) {
        // configure SSL.
        val sslContext: SslContext? = if (SSL) {
            SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
        } else {
            null;
        }

        val group = NioEventLoopGroup();
        try {
            val bootstrap: Bootstrap = Bootstrap();

            bootstrap
                .group(group)
                .channel(NioSocketChannel::class.java)
                .handler(ControlConnectionInitializer(sslContext));

            LOG.info("start the control connection attempt");
            //LOG.debug("start the connection attempt")

            // start the connection attempt.
            var ch: Channel = bootstrap
                .connect(HOST, PORT)
                .sync()
                .channel();


            // reads commands from the stdin.
            var lastWriteFuture: ChannelFuture? = null;
            var bufferedReader: BufferedReader = BufferedReader(InputStreamReader(System.`in`));
            while (true) {
                var line = bufferedReader.readLine() ?: break;

                // sends the line from stdin via channel.
                lastWriteFuture = ch.writeAndFlush("${line}\r\n");


                // if user types the 'bye' command, wait until the server closes
                // the connection.
                if ("bye".equals(line.toLowerCase())) {
                    ch
                        .closeFuture()
                        .sync();

                    break;
                }
            }

            // wait until all messages are flushed before closing the channel.
            lastWriteFuture?.sync();


        } finally {
            group.shutdownGracefully();
        }
    }
}