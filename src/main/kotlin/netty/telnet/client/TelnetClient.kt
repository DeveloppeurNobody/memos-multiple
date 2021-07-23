package netty.telnet.client

import io.netty.bootstrap.Bootstrap
import io.netty.channel.*
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.DelimiterBasedFrameDecoder
import io.netty.handler.codec.Delimiters
import io.netty.handler.codec.string.StringDecoder
import io.netty.handler.codec.string.StringEncoder

import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory

class TelnetClientService {
    var response = "";
    var clientChannel: Channel? = null;
    var clientChannelFuture: ChannelFuture? = null;

    private val executor = Executors.newSingleThreadExecutor(ThreadFactory { it -> Thread(it, "CC") });

    fun startCC() {
        println("[ ${Thread.currentThread().name} ] startClient")
        executeTask(TelnetClient());
    }

    fun doCommand() {
        println("[ ${Thread.currentThread().name} ] startClient")
        executeTask(TaskCommand());
    }

    fun executeTask(task: Runnable) {
        println("[ ${Thread.currentThread().name} ] executeTask() A new task has arrived");
        executor.submit(task)
    }

    fun endServer() {
        println("[ ${Thread.currentThread().name} ] endServer()");
        executor.shutdown();
    }


    inner class TelnetClient : Runnable {
        override fun run() {
            println("- [ ${Thread.currentThread().name} ] run()")


            try {
                clientChannelFuture = startConnection().connect("192.168.1.13", 21);
                clientChannelFuture?.addListener {
                    if (it.isSuccess) {
                        println("-- [ ${Thread.currentThread().name} ] CC connected")
                        clientChannel = clientChannelFuture?.channel();
                    }
                }


            } catch (ex: Exception) {
                println("- [ ${Thread.currentThread().name} ] run() #ex: ${ex.printStackTrace()}")
            }
        }

        fun startConnection(): Bootstrap {

            val group: EventLoopGroup = NioEventLoopGroup();
            try {
                val bootstrap: Bootstrap = Bootstrap();
                bootstrap
                    .group(group)
                    .channel(NioSocketChannel::class.java)
                    .handler(object : ChannelInitializer<SocketChannel>() {
                        override fun initChannel(ch: SocketChannel?) {
                            val pipeline: ChannelPipeline = ch!!.pipeline();
                            with(pipeline) {
                                addLast("delimiter", DelimiterBasedFrameDecoder(8192, *Delimiters.lineDelimiter()));
                                addLast("stringDecoder", StringDecoder());
                                addLast("stringEncoder", StringEncoder());
                                addLast(TelHandler());
                            }
                        }
                    });

                return bootstrap;

            } catch (ex: Exception) {
                throw ex;
            }
        }
    }

    inner class TaskCommand : Runnable {
        override fun run() {
            clientChannelFuture?.addListener {
                if (it.isSuccess) {
                    clientChannelFuture?.channel()
                        ?.writeAndFlush("user ken \r\n")
                        ?.addListener {
                            println("-- [ ${Thread.currentThread().name} ] user sent")
                        }
                }
            }
        }
    }
}

class TelHandler : SimpleChannelInboundHandler<String>() {

    override fun channelActive(ctx: ChannelHandlerContext?) {
        println("-- [ ${Thread.currentThread().name} ] TelHandler.channelActive()")
    }

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: String?) {
        println("-- [ ${Thread.currentThread().name} ] TelHandler.channelRead0() #msg: $msg");
    }

    override fun channelInactive(ctx: ChannelHandlerContext?) {
        println("-- [ ${Thread.currentThread().name} ] TelHandler.channelInactive()");
    }
}