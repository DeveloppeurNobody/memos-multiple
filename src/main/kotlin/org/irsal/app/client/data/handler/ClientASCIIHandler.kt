package org.irsal.app.client.data.handler

import org.irsal.app.client.GenerateLogger
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

class ClientASCIIHandler : SimpleChannelInboundHandler<String>() {
    val LOG = GenerateLogger.getLoggerForClass(this);

    override fun channelActive(ctx: ChannelHandlerContext?) {
        LOG.info("channelActive()");
    }

//    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
//        LOG.info("channelRead()#msg: $msg");
//    }

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: String?) {
        LOG.info(" - channelRead0()#msg: $msg");
    }

    override fun channelInactive(ctx: ChannelHandlerContext?) {
        LOG.info("channelInactive()");
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        LOG.error("exceptionCaught()");
        cause?.printStackTrace();
        ctx?.close();
    }


}
