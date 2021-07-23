package org.irsal.app.client.control.initializer

import org.irsal.app.client.GenerateLogger
import org.irsal.app.client.control.handler.ControlConnectionHandler
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelPipeline
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.DelimiterBasedFrameDecoder
import io.netty.handler.codec.Delimiters
import io.netty.handler.codec.string.StringDecoder
import io.netty.handler.codec.string.StringEncoder
import io.netty.handler.ssl.SslContext

class ControlConnectionInitializer(val sslCtx: SslContext?) : ChannelInitializer<SocketChannel>() {
    val LOG = GenerateLogger.getLoggerForClass(this);

    companion object {
        val DECODER: StringDecoder = StringDecoder();
        val ENCODER: StringEncoder = StringEncoder();

        val CLIENT_HANDLER: SimpleChannelInboundHandler<String> = ControlConnectionHandler();
    }

    override fun initChannel(ch: SocketChannel?) {
        val pipeline: ChannelPipeline? = ch?.pipeline();

        if (pipeline != null) {
            with(pipeline) {

                // addLast("LoggerHandler", LoggingHandler(LogLevel.INFO));

                    // add the text line codec combination first
                    addLast(DelimiterBasedFrameDecoder(8192, *Delimiters.lineDelimiter()));
                    addLast(DECODER);
                    addLast(ENCODER)

                    // and then busines logic.
                    addLast(CLIENT_HANDLER);
            }

        }
    }
}