package coroutines

import io.netty.bootstrap.Bootstrap
import io.netty.channel.*
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.DelimiterBasedFrameDecoder
import io.netty.handler.codec.Delimiters
import io.netty.handler.codec.string.StringDecoder
import io.netty.handler.codec.string.StringEncoder
import io.netty.util.concurrent.Future
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumesAll
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object AppCoroutines05_Telnet_Netty {
    var host = "192.168.1.2";
    var port = 21;

    @JvmStatic
    fun main(args: Array<String>) {
        var currentResponse = "";
        var appClient = AppClient();
        println("main() attempt to start client");

        runBlocking {
            val result = appClient.startClient();


            println("result: $result currentMessage: ${AppClientHandler.lastMessage}");
            println("end async")
        }

        // pas le dernier message car retourne avant la reception trouver un moyen channel actor
        currentResponse = AppClientHandler.lastMessage;
        println("main() end => currentResponse: $currentResponse");
    }
}


class AppClient {

    suspend fun startClient(): String =

        suspendCoroutine { continuation ->
            val callback = ChannelFutureListener {
                if (it.isSuccess) {
                    continuation.resume("connected");
                }
            }

            var host = "192.168.1.2";
            var port = 21;

            val group: EventLoopGroup = NioEventLoopGroup();
            try {
                val bootstrap: Bootstrap = Bootstrap();

                val channelFuture = bootstrap
                    .group(group)
                    .channel(NioSocketChannel::class.java)
                    .handler(object : ChannelInitializer<SocketChannel>() {
                        override fun initChannel(ch: SocketChannel?) {
                            val pipeline: ChannelPipeline = ch!!.pipeline();
                            with(pipeline) {
                                addLast("delimiter", DelimiterBasedFrameDecoder(8192, *Delimiters.lineDelimiter()));
                                addLast("stringDecoder", StringDecoder());
                                addLast("stringEncoder", StringEncoder());
                                addLast("appClientHandler", AppClientHandler());
                            }
                        }
                    })
                    .connect(host, port)
                    .sync()
                    .addListener(callback);

            } catch (ex: Exception) {
                continuation.resumeWithException(ex);
            }
        }

}

//
//fun startConnection(): ChannelFuture {
//    var host = "192.168.1.2";
//    var port = 21;
//
//    val group: EventLoopGroup = NioEventLoopGroup();
//    try {
//        val bootstrap: Bootstrap = Bootstrap();
//
//        val channelFuture = bootstrap
//            .group(group)
//            .channel(NioSocketChannel::class.java)
//            .handler(object : ChannelInitializer<SocketChannel>() {
//                override fun initChannel(ch: SocketChannel?) {
//                    val pipeline: ChannelPipeline = ch!!.pipeline();
//                    with(pipeline) {
//                        addLast("delimiter", DelimiterBasedFrameDecoder(8192, *Delimiters.lineDelimiter()));
//                        addLast("stringDecoder", StringDecoder());
//                        addLast("stringEncoder", StringEncoder());
//                        addLast("appClientHandler", AppClientHandler());
//                    }
//                }
//            })
//            .connect(host, port)
//            .sync()
//
//        return channelFuture;
//
//
//    } catch (ex: Exception) {
//        throw ex;
//    }
//}

//}

class AppClientHandler : SimpleChannelInboundHandler<String>() {
    companion object {
        var lastMessage = "empty";
    }

    override fun channelActive(ctx: ChannelHandlerContext?) {
        lastMessage = "channelActive";
    }

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: String?) {
        println("[ AppClientHandler ] channelRead0() #msg: $msg");
        lastMessage = msg ?: "nothing";
    }
}