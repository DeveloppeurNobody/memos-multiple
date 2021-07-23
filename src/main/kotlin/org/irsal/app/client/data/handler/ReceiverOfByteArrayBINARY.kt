package org.irsal.app.client.data.handler

import org.irsal.app.client.GenerateLogger
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*
import kotlin.Exception

class ReceiverOfByteArrayBINARY(
    var position: Long = 0L
) : SimpleChannelInboundHandler<ByteArray>() {

    val LOG = GenerateLogger.getLoggerForClass(this);

    var fileChannelFrom: FileChannel? = null;

    var pathTo: Path = Paths.get("/home/ryu/storyyCopie.txt");
    lateinit var pathFrom: Path;




    override fun channelActive(ctx: ChannelHandlerContext?) {
        LOG.info("channelActive()");
        openFileChannel();
    }

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: ByteArray?) {
        LOG.info(" - channelRead0()");

        // buffer netty -> to byteArray -> byteBuffer
        val buffer: ByteBuffer = ByteBuffer.wrap(msg as ByteArray);

        //LOG.info(String(msg!!));

        val disposable = writeByteArrayToFileChannel(buffer)
            .subscribeOn(Schedulers.parallel())
            .subscribe(
                {
                    LOG.info("#Mono write bytes ok");
                },
                {
                    LOG.error("#Mono exception: ${it.printStackTrace()}");
                }
            )
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext?) {

    }

    override fun channelInactive(ctx: ChannelHandlerContext?) {
        LOG.info("channelInactive() and closing fileChannel");
        fileChannelFrom?.close();
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        LOG.error(cause?.message);
        ctx?.close();
    }

    @Throws(Exception::class)
    fun openFileChannel() {
        LOG.info(" - openFileChannel() #position: $position");
        try {
            // attempt to open
            fileChannelFrom = FileChannel.open(pathTo, EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.APPEND));
            fileChannelFrom!!.position(position);
        } catch (ex: Exception) {
            throw ex;
        }
    }

    @Throws(Exception::class)
    fun writeByteArrayToFileChannel(buffer: ByteBuffer): Mono<Boolean> {
        LOG.info(" - writeByteArrayToFileChannel");
        return Mono.fromCallable {
            try {
                while (buffer.hasRemaining()) {
                    fileChannelFrom!!.write(buffer);
                }

                true;
            } catch (ex: Exception) {
                throw ex;
            }
        }
    }

}