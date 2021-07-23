package netty.custom.telnet.server

import io.netty.channel.*
import java.net.InetAddress
import java.util.*

class TelnetServerHandler : SimpleChannelInboundHandler<String>() {

    override fun channelActive(ctx: ChannelHandlerContext?) {
        // send greeting for a new connection.
        ctx?.write("Welcome to ${InetAddress.getLocalHost().hostName}!\r\n");
        ctx?.write("It is ${Date()} now.\r\n");

        ctx?.flush();
    }

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: String?) {
        // Generate and write a response.
        var response: String = "";
        var close = false;

        if (msg.isNullOrBlank()) {
            response = "Please type something.\r\n";
        } else if ("bye".equals(msg.toLowerCase())) {
            response = "Have a good day!\r\n";
            close = true;
        } else {
            response = "Did you say '${msg}'? \r\n";
        }

        // we do not need to write a ChannelBuffer here.
        // we know the encoder inserted at TelnetPipelineFactory will do the convertion
        val future: ChannelFuture? = ctx?.write(response);

        // close the connection after sending 'Have a good day!'
        // if the client has sent 'bye'.
        if (close) {
            future?.addListener { ChannelFutureListener.CLOSE }
        }
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext?) {
        ctx?.flush();
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        cause?.printStackTrace();
        ctx?.close();
    }
}