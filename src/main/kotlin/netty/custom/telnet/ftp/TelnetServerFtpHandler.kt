package netty.custom.telnet.ftp

import io.netty.channel.*

@ChannelHandler.Sharable
class TelnetServerFtpHandler : SimpleChannelInboundHandler<String>() {



    override fun channelActive(ctx: ChannelHandlerContext?) {
        println("channelActive()");
       // ctx?.writeAndFlush("pasv\r\n");
    }


    override fun channelRead0(ctx: ChannelHandlerContext?, msg: String?) {
        var close = false;

        println("channelRead0(): $msg");

//        // close data connection when msg is received
//        if (!msg.isNullOrBlank()) {
//            close = true;
//        }
//
//        // closing channel when done!
//        if (close) {
//            ctx?.close();
//        }
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
}