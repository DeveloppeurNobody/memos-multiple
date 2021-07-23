package netty.factorial.client

import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelPipeline
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.compression.ZlibCodecFactory
import io.netty.handler.codec.compression.ZlibWrapper
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.handler.ssl.SslContext
import netty.factorial.codecs.decoders.BigIntegerDecoder
import netty.factorial.codecs.encoders.NumberEncoder

/**
 * Creates a newly configured {@link ChannelPipeline} for a client-side channel
 */
class FactorialClientInitializer(private val sslCtx: SslContext?) : ChannelInitializer<SocketChannel>() {

    override fun initChannel(ch: SocketChannel?) {
        if (ch != null) {
            var pipeline: ChannelPipeline = ch.pipeline();
            with(pipeline) {
                if (sslCtx != null) {
                    addLast(sslCtx.newHandler(ch.alloc(), FactorialClient.HOST, FactorialClient.PORT));
                }

                // for logging
                addLast(LoggingHandler(LogLevel.INFO));

                // enable stream compression (you can remove these two if unnecessary)
                addLast(ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));
                addLast(ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP));

                // add the number codec first
                addLast(BigIntegerDecoder());
                addLast(NumberEncoder());

                // and then busines logic.
                addLast(FactorialClientHandler());
            }
        }
    }
}