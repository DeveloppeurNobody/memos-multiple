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
import netty.telnet.executor.ControlConnectionEventBus

class ControlConnection : Runnable {
    var ccFuture: ChannelFuture? = null;
    var ccChannel: Channel? = null;

    override fun run() {
        println("- [ ${Thread.currentThread().name} ] run()");
        startANewConnection();
        println("- [ ${Thread.currentThread().name} ] run() >>> end");
    }

    fun startANewConnection() {
        println("- [ ${Thread.currentThread().name} ] startANewConnection()");
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
                    })

                ccFuture = bootstrap.connect("192.168.1.13", 21);
                ccFuture?.addListener {
                        if (it.isSuccess) {
                            println("-- [ ${Thread.currentThread().name} ] CC connected !")
                            ccChannel = ccFuture?.channel();
                            println("-- [ ${Thread.currentThread().name} ] CC before updating eventBus !")
                            ControlConnectionEventBus.COMMAND_STATE.onNext(true);
                            println("-- [ ${Thread.currentThread().name} ] CC after updating eventBus !")
                        }
                    }

            } catch (ex: Exception) {
                ControlConnectionEventBus.COMMAND_STATE.onError(ex);
                throw ex;
            }
        }
}