package org.irsal.app.client.data.initializer

import org.irsal.app.client.data.ClientPassiveDataConnection.isASCII
import org.irsal.app.client.GenerateLogger
import org.irsal.app.client.data.handler.ClientASCIIHandler
import org.irsal.app.client.data.handler.ReceiverOfByteArrayBINARY
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelPipeline
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.DelimiterBasedFrameDecoder
import io.netty.handler.codec.Delimiters
import io.netty.handler.codec.LengthFieldPrepender
import io.netty.handler.codec.bytes.ByteArrayDecoder
import io.netty.handler.codec.bytes.ByteArrayEncoder
import io.netty.handler.codec.string.StringDecoder
import io.netty.handler.codec.string.StringEncoder
import io.netty.handler.ssl.SslContext


class ClientInitializer(val sslCtx: SslContext?) : ChannelInitializer<SocketChannel>() {
    val LOG = GenerateLogger.getLoggerForClass(this);

    companion object {
        val DECODER: StringDecoder = StringDecoder();
        val ENCODER: StringEncoder = StringEncoder();

        val CLIENT_HANDLER: SimpleChannelInboundHandler<String> = ClientASCIIHandler();
    }

    override fun initChannel(ch: SocketChannel?) {
        val pipeline: ChannelPipeline? = ch?.pipeline();

        if (pipeline != null) {
            with(pipeline) {

               // addLast("LoggerHandler", LoggingHandler(LogLevel.INFO));

                if (isASCII) {
                    LOG.info("isASCII: $isASCII")
                    // add the text line codec combination first
                    addLast(DelimiterBasedFrameDecoder(8192, *Delimiters.lineDelimiter()));
                    addLast(DECODER);
                    addLast(ENCODER)

                    // and then busines logic.
                    addLast(CLIENT_HANDLER);
                } else {
                    LOG.info("isASCII: $isASCII")

                    //addLast(ChunkedWriteHandler());

                    addHandlersBinaryForReceiver(this);

                    // Business
                    pipeline.addLast("ReceiverOfByteArrayBINARY", ReceiverOfByteArrayBINARY());

                }
            }

        }
    }

    private fun addHandlersBinaryForReceiver(pipeline: ChannelPipeline) {
        //=========================
        // BINARY MODE FOR LIST
        //=========================

        // Decoders
        pipeline.addLast("bytesDecoder", ByteArrayDecoder());

        // Encoder
        pipeline.addLast("frameEncoder", LengthFieldPrepender(4));
        pipeline.addLast("bytesEncoder", ByteArrayEncoder());


    }

}