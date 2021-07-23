package netty.custom.telnet.client

import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelPipeline
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.DelimiterBasedFrameDecoder
import io.netty.handler.codec.Delimiters
import io.netty.handler.codec.LengthFieldBasedFrameDecoder
import io.netty.handler.codec.LengthFieldPrepender
import io.netty.handler.codec.bytes.ByteArrayDecoder
import io.netty.handler.codec.bytes.ByteArrayEncoder
import io.netty.handler.codec.string.StringDecoder
import io.netty.handler.codec.string.StringEncoder
import io.netty.handler.ssl.SslContext
import netty.custom.telnet.ftp.ClientForDataConnectionPassive_Old.isASCII_old


class TelnetClientInitializer(val sslCtx: SslContext?) : ChannelInitializer<SocketChannel>() {
    companion object {
        val DECODER: StringDecoder = StringDecoder();
        val ENCODER: StringEncoder = StringEncoder();

        val CLIENT_HANDLER: SimpleChannelInboundHandler<String> = TelnetClientHandler();
    }

    override fun initChannel(ch: SocketChannel?) {
        val pipeline: ChannelPipeline? = ch?.pipeline();

        if (pipeline != null) {
            with(pipeline) {



                if (isASCII_old) {
                    println("isAscii: $isASCII_old")
                    // add the text line codec combination first
                    addLast(DelimiterBasedFrameDecoder(8192, *Delimiters.lineDelimiter()));
                    addLast(DECODER);
                    addLast(ENCODER)

                    // and then busines logic.
                    addLast(CLIENT_HANDLER);
                } else {
                    println("isAscii: $isASCII_old")


                    //addLast(ChunkedWriteHandler());
                    //addLast(ReceiverOfFileData());
                    //addLast(SenderOfFileData())

                    //=========================
                    // BINARY MODE FOR LIST
                    //=========================

                    // Decoders

                    // Decoders
                    pipeline.addLast("frameDecoder", LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4));
                    pipeline.addLast("bytesDecoder", ByteArrayDecoder());

                    // Encoder
                    pipeline.addLast("frameEncoder", LengthFieldPrepender(4));
                    pipeline.addLast("bytesEncoder", ByteArrayEncoder());

                    // Business
                    addLast("readerListHandler", ReaderListBinary());
                }
            }

        }
    }

}