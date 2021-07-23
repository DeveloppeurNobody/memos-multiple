package netty.filenio.client

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
import io.netty.handler.stream.ChunkedNioFile
import netty.echo.client.EchoClientHandler
import java.nio.channels.FileChannel
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

object FileNioClient {

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
                .handler(FileNioClientInitializer(sslCtx));

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