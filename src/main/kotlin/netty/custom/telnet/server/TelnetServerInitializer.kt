package netty.custom.telnet.server

import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelPipeline
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.DelimiterBasedFrameDecoder
import io.netty.handler.codec.Delimiters
import io.netty.handler.codec.string.StringDecoder
import io.netty.handler.codec.string.StringEncoder
import io.netty.handler.ssl.SslContext

class TelnetServerInitializer(val sslCtx: SslContext?) : ChannelInitializer<SocketChannel>() {
    companion object {
        val DECODER = StringDecoder();
        val ENCODER = StringEncoder();

        val SERVER_HANDLER = TelnetServerHandler();
    }

    override fun initChannel(ch: SocketChannel?) {
        val pipeline: ChannelPipeline? = ch?.pipeline();

        if (pipeline != null) {
            with(pipeline) {
                if (sslCtx != null) {
                    addLast(sslCtx.newHandler(ch.alloc()));
                }

                // add the text line codex combination first,
                addLast(DelimiterBasedFrameDecoder(8192, *Delimiters.lineDelimiter()));

                // add encoder and decoder
                addLast(DECODER);
                addLast(ENCODER);

                // and then busines logic
                addLast(SERVER_HANDLER);
            }
        }
    }
}