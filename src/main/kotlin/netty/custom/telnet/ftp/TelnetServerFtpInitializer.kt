package netty.custom.telnet.ftp

import io.netty.buffer.ByteBuf
import io.netty.channel.*
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.DelimiterBasedFrameDecoder
import io.netty.handler.codec.Delimiters
import io.netty.handler.codec.bytes.ByteArrayDecoder
import io.netty.handler.codec.bytes.ByteArrayEncoder
import io.netty.handler.codec.compression.ZlibCodecFactory
import io.netty.handler.codec.compression.ZlibWrapper
import io.netty.handler.codec.string.StringDecoder
import io.netty.handler.codec.string.StringEncoder
import io.netty.handler.ssl.SslContext
import io.netty.handler.stream.ChunkedNioFile
import io.netty.handler.stream.ChunkedWriteHandler
import netty.custom.telnet.ftp.ServerForDataConnectionActive.isServerASCII
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

class TelnetServerFtpInitializer(val sslCtx: SslContext?) : ChannelInitializer<SocketChannel>() {

    var transferMode = TransferMode_Old.COMPRESSED_MODE;

    companion object {
        val DECODER = StringDecoder();
        val ENCODER = StringEncoder();

        val BYTE_ARRAY_DECODER = ByteArrayDecoder();
        val BYTE_ARRAY_ENCODER = ByteArrayEncoder();

        val SERVER_HANDLER = TelnetServerFtpHandler();
    }

    override fun initChannel(ch: SocketChannel?) {
        val pipeline: ChannelPipeline? = ch?.pipeline();

        if (pipeline != null) {
            with(pipeline) {
                if (sslCtx != null) {
                    addLast(sslCtx.newHandler(ch.alloc()));
                }


                println("isASCII => $isServerASCII");
                transferMode = TransferMode_Old.STREAM_MODE;


                println("server ascii flag: $isServerASCII");

                if (isServerASCII) {
                    // add the text line codex combination first,
                    addLast(DelimiterBasedFrameDecoder(8192, *Delimiters.lineDelimiter()));

                    // add encoder and decoder
                    addLast(DECODER);
                    addLast(ENCODER);

                    // add zipMode if compressed is enabled
                    if (transferMode.equals(TransferMode_Old.COMPRESSED_MODE)) {
                        println("ZlibCodec added");
                        addLast(ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));
                        addLast(ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP))
                    }

                    // and then busines logic
                    addLast(SERVER_HANDLER);

                } else {
                    // data is proceed as BYTE ARRAY
//                    addLast(BYTE_ARRAY_DECODER);
//                    addLast(BYTE_ARRAY_ENCODER)

                    // add zipMode if compressed is enabled
                    if (transferMode.equals(TransferMode_Old.COMPRESSED_MODE)) {
                        println("ZlibCodec added");
                        addLast(ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));
                        addLast(ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP))
                    }

                    addLast("chunkedWriterHandler", ChunkedWriteHandler());

                    // business for data file.
                    val sendData = false;

                    if (sendData) {
                        println("adding SenderDataAsChunkedWriterHandler");

                        // send from local to remote server
                        addLast(SenderDataAsChunkedWriterHandler());
                    }
                    else {
                        println("adding ServerReceiverOfFileData")
                        // receive from remote server to local
                        addLast(ServerReceiverOfFileData());
                    }


                }
            }
        }
    }


    inner class ServerReceiverOfFileData : ChannelInboundHandlerAdapter() {
        val pathTo: Path = Paths.get("/home/ryu", "server_retr_file");

        lateinit var fileChannel: FileChannel;
        private var fileIsNotComplete: Boolean = true;
        var capacity = -1;

        var fileSize: Long = 0;
        lateinit var pathFrom: Path;


        lateinit var channelFuture: ChannelFuture;

        override fun channelActive(ctx: ChannelHandlerContext?) {
            println("[ 2 - ${this::class.java.simpleName} ] channelActive())");
            openFileChannel();
        }

        override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
            println("[ 2 - ${this::class.java.simpleName} ] channelRead new msg *** : $msg");
            when (msg) {
                is ByteBuf -> {
                    println("channelRead0(): $msg");
                    appendDataToFile(msg);
                }
                else -> {
                    println("msg is not ByteBuf");
                }
            }

            println("[ 2 - ${this::class.java.simpleName} ] -----------------------------------------------");
        }

        override fun channelReadComplete(ctx: ChannelHandlerContext?) {

        }

        override fun channelInactive(ctx: ChannelHandlerContext?) {
            println("channelInactive() ****");
            fileChannel?.close();

        }



        override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
            println("[ 2 - ${this::class.java.simpleName} ] exception");
            println("[ 2 - ${this::class.java.simpleName} ] cause: ${cause?.printStackTrace()}");
            println("[ 2 - ${this::class.java.simpleName} ] -----------------------------------------------");
        }

        private fun openFileChannel() {
            try {
                // Separation of creation of file from fileChannel
                //Files.deleteIfExists(path);
                fileChannel = FileChannel.open(pathTo, EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.APPEND))

            } catch (e: java.lang.Exception) {
                System.err.println(e);
            }
        }

        private fun appendDataToFile(msg: ByteBuf) {
            var byteBuf: ByteBuf = msg;
            var byteBuffer: ByteBuffer = byteBuf.nioBuffer();

            try {
                while (byteBuffer.hasRemaining()) {
                    fileChannel.write(byteBuffer);
                }


            } catch (e: java.lang.Exception) {
                System.err.println("Exception: $e")
            }
        }
    }

    inner class SenderDataAsChunkedWriterHandler : ChannelInboundHandlerAdapter() {
        var pathFrom: Path = Paths.get("/home/ryu/raf", "story.txt");

        lateinit var chunkedNioFile: ChunkedNioFile;

        var logGroup: MutableList<String> = mutableListOf();
        var valueTransfer: Long = 0;
        var stringBuilder: StringBuilder = StringBuilder();
        lateinit var sendFileChannelFuture: ChannelFuture;

        lateinit var fileChannelFrom: FileChannel;
        lateinit var futureCanal: ChannelFuture;

        override fun channelActive(ctx: ChannelHandlerContext?) {
            println("[ ${this::class.java.simpleName} ] [ channelActive ] Attempt to open fileChannel");

            // opening fileChannelFrom
            openFileChannel();

            println("[ ${this::class.java.simpleName} ] [ channelActive ] fileChannel opened.");

            println("[ ${this::class.java.simpleName} ] [ channelActive ] Attempt to send chunks...");
            // Attempt to transfer
            transferViaChunker(ctx!!);

            println("[ ${this::class.java.simpleName} ] [ channelActive ] END ***");
        }

        override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
            println("channelRead(): msg: $msg");
        }

        override fun channelReadComplete(ctx: ChannelHandlerContext?) {
            ctx?.flush();
        }

        override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
            cause?.printStackTrace();
            ctx?.close();
        }

        override fun channelInactive(ctx: ChannelHandlerContext?) {
            println("channelInactive()");
        }

        private fun openFileChannel() {
            try {
                fileChannelFrom = FileChannel
                    .open(pathFrom, EnumSet.of(StandardOpenOption.READ));
            } catch (ex: Exception) {
                println("ex: ${ex.printStackTrace()}");
            }
        }

        private fun transferViaChunker(ctx: ChannelHandlerContext) {
            println("****************** Attempt to open FileChannel");

            chunkedNioFile = ChunkedNioFile(fileChannelFrom);

            // Write the content.
            sendFileChannelFuture = ctx.writeAndFlush(chunkedNioFile, ctx.newProgressivePromise());

            sendFileChannelFuture.addListener(object : ChannelProgressiveFutureListener {

                override fun operationProgressed(future: ChannelProgressiveFuture?, progress: Long, total: Long) {
                    if (total < 0) { // total unknown
                        println("${future?.channel()} Transfer progress: $progress")
                    } else {
                        valueTransfer = (progress * 100) / total;
                        println("Transfer progress: $progress / $total | $valueTransfer %");
                    }
                }

                override fun operationComplete(future: ChannelProgressiveFuture?) {
                    println("[ ${this::class.java.simpleName} ] //// transferViaChunker => operationComplete => closing fileChannelFrom");
                    fileChannelFrom.close();

                    println(
                        """
                        transferViaChunder().operationComplete
                        => attempt to close channel.
                    """.trimIndent()
                    );
                    ctx
                        .close()
                        .addListener {
                            println("Channel closed!");
                        }
                }
            });

        }
    }
}