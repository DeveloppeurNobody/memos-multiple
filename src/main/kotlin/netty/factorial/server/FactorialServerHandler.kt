package netty.factorial.server

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import java.lang.Exception
import java.math.BigInteger

/**
 * Handler for server-side channel. This handler maintains stateful
 * information which is specific to a certain channel using member variables.
 * Therefore, an instance of this handler can cover only one channel. You have
 * to create a new handler instance whenever tou create a new channel and
 * this handler to avoid race condition.
 */
class FactorialServerHandler : SimpleChannelInboundHandler<BigInteger>() {
    private var lastMultiplayer: BigInteger = BigInteger("1");
    private var factorial: BigInteger = BigInteger("1");

    @Throws(Exception::class)
    override fun channelRead0(ctx: ChannelHandlerContext?, msg: BigInteger?) {
        // calculate the cumulative factorial and send it to the client.
        if (msg != null) {
            lastMultiplayer = msg;
            factorial = factorial.multiply(msg);
            ctx?.writeAndFlush(factorial);
        }
    }

    @Throws(Exception::class)
    override fun channelInactive(ctx: ChannelHandlerContext?) {
        System.err.println("Factorial of $lastMultiplayer is $factorial");
    }

    @Throws(Exception::class)
    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        cause?.printStackTrace();
        ctx?.close();
    }
}