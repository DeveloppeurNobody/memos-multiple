package netty.custom.telnet.ftp

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.SelfSignedCertificate
import netty.custom.telnet.ftp.service.PortService_Old
import java.net.InetSocketAddress

/**
 * Simplistic telnet server ftp for testing
 * port should start at 3855
 */
object ServerForDataConnectionActive {

    // IS ASCII
    var isServerASCII = true;

    val SSL = System.getProperty("ssl") != null;
    //val PORT = System.getProperty("port", if (SSL) { "8992" } else { "3855" }).toInt();

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

            val portService = PortService_Old();
            val PORT = portService.getFreePort();

            val localInetSocketAddressed = InetSocketAddress("127.0.0.1", PORT);
            println("encoded address: ${portService.encode(localInetSocketAddressed)}");


            val serverBootstrap: ServerBootstrap = ServerBootstrap();
            serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
                .handler(LoggingHandler(LogLevel.INFO))
                .childHandler(TelnetServerFtpInitializer(sslCtx));

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