package netty.filenio.client

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.handler.stream.ChunkedNioFile
import java.nio.channels.FileChannel
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

class FileNioClientHandler : ChannelInboundHandlerAdapter() {
    private val path: Path = Paths.get("/home/ryu/raf/email", "wiki.txt");

    //

    override fun channelActive(ctx: ChannelHandlerContext) {
        super.channelActive(ctx);
        FileChannel.open(path, EnumSet.of(StandardOpenOption.READ))
            .use { fileChannel ->
                ctx.writeAndFlush(ChunkedNioFile(fileChannel));
            }

    }

    //------------------------------------------------------------------------------
    // --- with offset
    //
    //Send the 10MB File in 1MB chunks as specified by the following chunk size (1024*1024*1)
    // ctx.write(ChunkedNioFile(theFileChannel, offSet, fileLength, 1024 * 1024 * 1))
    //
    //------------------------------------------------------------------------------
    // --- Zero copy or NOT
    //    ctx.write("OK: " + raf.length() + '\n');
    //    if (ctx.pipeline().get(SslHandler.class) == null) {
    //        // SSL not enabled - can use zero-copy file transfer.
    //        ctx.write(new DefaultFileRegion(raf.getChannel(), 0, length));
    //    } else {
    //        // SSL enabled - cannot use zero-copy file transfer.
    //        ctx.write(new ChunkedFile(raf));
    //    }
    //    ctx.writeAndFlush("\n");
    //
    // ---------------------------------------------------------------------------
    //
    //    @Override
    //    protected void channelRead(ChannelHandlerContext ctx, Object msg)
    //    throws Exception {
    //
    //        File file = new File(dest);//remember to change dest
    //
    //        if (!file.exists()) {
    //            file.createNewFile();
    //        }
    //
    //        ByteBuf byteBuf = (ByteBuf) msg;
    //        ByteBuffer byteBuffer = byteBuf.nioBuffer();
    //        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
    //        FileChannel fileChannel = randomAccessFile.getChannel();
    //
    //        while (byteBuffer.hasRemaining()){;
    //            fileChannel.position(file.length());
    //            fileChannel.write(byteBuffer);
    //        }
    //
    //        byteBuf.release();
    //        fileChannel.close();
    //        randomAccessFile.close();
    //
    //    }}

}
