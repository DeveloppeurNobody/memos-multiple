package netty.factorial.server

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
import java.math.BigInteger

/**
 * Creates a newly configured {@link ChannelPipeline} for server-side channel.
 */
class FactorialServerInitializer(private val sslCtx: SslContext?) : ChannelInitializer<SocketChannel>() {
    override fun initChannel(ch: SocketChannel?) {
        if (ch != null) {
            var pipeline: ChannelPipeline = ch.pipeline();
            with (pipeline) {
                if (sslCtx != null) {
                    addLast(sslCtx.newHandler(ch.alloc()));
                }
                // enable stream compression (you can remove these two if unnecessary)
                addLast(ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP));
                addLast(ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));

                // add the number codec first.
                addLast(BigIntegerDecoder());
                addLast(NumberEncoder());

                // and then busines logic
                // /!\ Please note we create a handler for every new channel
                // because it has stateful properties /!\
                addLast(FactorialServerHandler());
            }
        }
    }
}