package nio2.chap09_async

import java.io.IOException
import java.net.InetSocketAddress
import java.net.StandardSocketOptions
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.AsynchronousSocketChannel
import java.util.concurrent.Future

object AsyncApp08AsynchronousServerSocket {
    @JvmStatic
    fun main(args: Array<String>) {
        val DEFAULT_PORT: Int = 5555;
        val IP: String = "127.0.0.1";

        // create asynchronous server-socket channel to bound to default group
        try {
            AsynchronousServerSocketChannel.open()
                .use { asynchronousServerSocketChannel ->

                    if (asynchronousServerSocketChannel.isOpen) {
                        asynchronousServerSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 4 * 1024);
                        asynchronousServerSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);

                        // bind the server-socket channel to local address
                        asynchronousServerSocketChannel.bind(InetSocketAddress(IP, DEFAULT_PORT));

                        // display a waiting message while ... waiting clients
                        println("Waiting for connections...");

                        while (true) {
                            var asynchronousSocketChannelFuture: Future<AsynchronousSocketChannel> =
                                asynchronousServerSocketChannel.accept();

                            try {
                                asynchronousSocketChannelFuture.get()
                                    .use { asynchronousSocketChannel ->
                                        println("Incoming connection from ${asynchronousSocketChannel.remoteAddress}");
                                        var byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(1024);

                                        //transmitting data
                                        while (asynchronousSocketChannel.read(byteBuffer).get() != -1) {
                                            byteBuffer.flip();
                                            asynchronousSocketChannel.write(byteBuffer).get();

                                            if (byteBuffer.hasRemaining()) {
                                                byteBuffer.compact();
                                            } else {
                                                byteBuffer.clear();
                                            }
                                        }

                                        println("${asynchronousSocketChannel.remoteAddress} was successfully served!");
                                    }
                            } catch (e: Exception) {
                                System.err.println(e);
                            }
                        }
                    } else {
                        println("The asynchronous server-socket channel cannot be opened!");
                    }

                }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}