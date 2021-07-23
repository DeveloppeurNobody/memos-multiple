package netty.factorial.client

import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import java.math.BigInteger
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

/**
 * Handler for client-side channel. This handler maintains stateful
 * information which is specific to a certain channel using member variables.
 * Therefore, an instance of this handler can cover only one channel. You have
 * to create a new handler instance whenever you create a new channel and insert
 * this handler to avoid race condition.
 */
class FactorialClientHandler : SimpleChannelInboundHandler<BigInteger>() {
    private lateinit var ctx: ChannelHandlerContext;
    private var receivedMessages: Int = -1;
    private var next: Int = 1;
    private val answer: BlockingQueue<BigInteger> = LinkedBlockingQueue<BigInteger>();

    fun getFactorial(): BigInteger {
        var interrupted: Boolean = false;
        try {
            while (true) {
                try {
                    return answer.take();
                } catch (ie: InterruptedException) {
                    System.err.println(ie);
                    interrupted = true;
                }
            }
        } finally {
            if (interrupted) {
                Thread.currentThread()
                    .interrupt();
            }
        }
    }

    override fun channelActive(ctx: ChannelHandlerContext?) {
        if (ctx != null) {
            this.ctx = ctx;
            sendNumbers();
        }
    }

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: BigInteger?) {
        receivedMessages++;
        if (receivedMessages == FactorialClient.COUNT) {
            // Offer the answer after closing the connection.
            ctx?.channel()
                ?.close()
                ?.addListener {
                    assert(answer.offer(msg));
                }
        }
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        cause?.printStackTrace();
        ctx?.close();
    }

    private fun sendNumbers() {
        // do not send more than 4096 numbers.
        var future: ChannelFuture? = null;
        var i: Int = 0;
        while (i < 4096 && next <= FactorialClient.COUNT) {
            future = ctx?.write(next);
            next++;
            i++;
        }
        if (next <= FactorialClient.COUNT) {
            assert(future != null);
            future?.addListener(ChannelFutureListener { futureChannel ->
                if (futureChannel != null) {
                    if (futureChannel.isSuccess) {
                        sendNumbers();
                    } else {
                        futureChannel.cause()
                            .printStackTrace();
                        futureChannel.channel()
                            .close();
                    }
                }
            });
        }
        ctx.flush();
    }
}