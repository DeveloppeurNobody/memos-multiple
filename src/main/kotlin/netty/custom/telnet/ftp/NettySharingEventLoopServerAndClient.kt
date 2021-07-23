package netty.custom.telnet.ftp

import io.netty.bootstrap.Bootstrap
import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import java.net.InetSocketAddress


/**
 * Listing 8.5 Bootstrapping a server
 *
 * @author [Norman Maurer](mailto:norman.maurer@gmail.com)
 * @author [Marvin Wolfthal](mailto:mawolfthal@gmail.com)
 */
class NettySharingEventLoopServerAndClient {
    /**
     * Listing 8.5 Bootstrapping a server
     */
    fun bootstrap() {
        val bootstrap = ServerBootstrap()
        bootstrap.group(NioEventLoopGroup(), NioEventLoopGroup())
            .channel(NioServerSocketChannel::class.java)
            .childHandler(
                object : SimpleChannelInboundHandler<ByteBuf?>() {
                    var connectFuture: ChannelFuture? = null

                    @Throws(Exception::class)
                    override fun channelActive(ctx: ChannelHandlerContext) {

                        // creation of client
                        val bootstrap = Bootstrap()
                        bootstrap.channel(NioSocketChannel::class.java)
                            .handler(object : SimpleChannelInboundHandler<ByteBuf?>() {
                                @Throws(Exception::class)
                                override fun channelRead0(ctx: ChannelHandlerContext, `in`: ByteBuf?) {
                                    println("Received data")
                                }
                            })

                        // using ctx from channelActive() above
                        bootstrap.group(ctx.channel().eventLoop())

                        // attempt to connect via client
                        connectFuture = bootstrap.connect(
                            InetSocketAddress("www.manning.com", 80)
                        )
                    }

                    @Throws(Exception::class)
                    override fun channelRead0(
                        channelHandlerContext: ChannelHandlerContext,
                        byteBuf: ByteBuf?
                    ) {
                        if (connectFuture!!.isDone) {
                            // do something with the data
                        }
                    }
                })
        val future = bootstrap.bind(InetSocketAddress(8080))
        future.addListener(ChannelFutureListener { channelFuture ->
            if (channelFuture.isSuccess) {
                println("Server bound")
            } else {
                System.err.println("Bind attempt failed")
                channelFuture.cause().printStackTrace()
            }
        })
    }
}
