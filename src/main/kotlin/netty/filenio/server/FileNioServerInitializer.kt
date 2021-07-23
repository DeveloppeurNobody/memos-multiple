package netty.filenio.server

import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelPipeline
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.handler.ssl.SslContext
import io.netty.handler.stream.ChunkedWriteHandler


class FileNioServerInitializer(private val sslCtx: SslContext?) : ChannelInitializer<Channel>() {

    override fun initChannel(ch: Channel?) {
        if (ch != null) {
            val pipeline: ChannelPipeline = ch.pipeline();

            with(pipeline) {
                if (sslCtx != null) {
                    addLast(sslCtx.newHandler(ch.alloc()));
                }
                addLast("logger", LoggingHandler(LogLevel.INFO));
                addLast("chunker", ChunkedWriteHandler());
                addLast("fileServerHandler", FileNioServerHandler());
            }
        }
    }
}