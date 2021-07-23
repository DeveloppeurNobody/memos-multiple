package netty.filenio.server

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.stream.ChunkedFile
import io.netty.handler.stream.ChunkedNioFile
import io.netty.handler.stream.ChunkedNioStream
import io.netty.handler.stream.ChunkedWriteHandler
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

class FileNioServerHandler : ChannelInboundHandlerAdapter() {
    //private val path: Path = Paths.get("/home/ryu", "rafa_copy_netty.mp4")
    private val path: Path = Paths.get("/home/ryu", "wiki_netty_copied.txt");


    override fun channelActive(ctx: ChannelHandlerContext?) {
        println("Connected");
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {

        println("Read msg received: $msg");
//        println("received data");
//        // chunkNioFile for later
//        var byteBuf: ByteBuf = msg as ByteBuf;
//        var byteBuffer: ByteBuffer = byteBuf.nioBuffer();
//
//        println("Attempt to read byteBuffer...")
//
//        FileChannel.open(path, EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE))
//            .use {fileChannel ->
//                while (byteBuffer.hasRemaining()) {
//                    fileChannel.write(byteBuffer);
//                }
//            }
    }




    override fun channelReadComplete(ctx: ChannelHandlerContext?) {
        super.channelReadComplete(ctx);
        println("received data complete");
    }
}
