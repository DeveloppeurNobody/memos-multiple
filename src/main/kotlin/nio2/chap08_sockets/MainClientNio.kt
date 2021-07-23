package nio2.chap08_sockets

import java.lang.Exception
import java.net.InetSocketAddress
import java.net.StandardSocketOptions
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.SocketChannel
import java.nio.charset.Charset
import java.nio.charset.CharsetDecoder
import java.util.*

object MainClientNio {
    const val DEFAULT_PORT: Int = 5555;
    const val IP: String = "127.0.0.1";

    @JvmStatic
    fun main(args: Array<String>) {
        val buffer: ByteBuffer = ByteBuffer.allocateDirect(2 * 1024);
        var randomBuffer: ByteBuffer;
        var charBuffer: CharBuffer;

        val charset: Charset = Charset.defaultCharset();
        val decoder: CharsetDecoder = charset.newDecoder();

        // open Selector and ServerSocketChannel by calling the open method
        try {
            Selector.open()
                .use { selector ->
                    SocketChannel.open()
                        .use { socketChannel ->

                            // check if both of them were successfully opened
                            if ( (socketChannel.isOpen) && (selector.isOpen) ) {
                                // configure non-blocking mode
                                socketChannel.configureBlocking(false);

                                // set some options
                                socketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 128 * 1024);
                                socketChannel.setOption(StandardSocketOptions.SO_SNDBUF, 128 * 1024);
                                socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);

                                // register the current channel with the given selector
                                socketChannel.register(selector, SelectionKey.OP_CONNECT);

                                // connect to remote host
                                socketChannel.connect(InetSocketAddress(IP, DEFAULT_PORT));

                                println("Localhost: ${socketChannel.localAddress}");

                                // waiting for the connection
                                while (selector.select(1000) > 0) {
                                    // get keys
                                    val keys = selector.selectedKeys();
                                    val its = keys.iterator();

                                    // process each key
                                    while (its.hasNext()) {
                                        val key: SelectionKey = its.next() as SelectionKey;

                                        its.remove();

                                        // get the socket channel for this key
                                        try {
                                            key.channel().use { keySocketChannel ->
                                                keySocketChannel as SocketChannel;

                                                if (keySocketChannel.isConnectionPending) {
                                                    keySocketChannel.finishConnect();
                                                }

                                                // read/write from/to server
                                                while (keySocketChannel.read(buffer) != -1) {
                                                    buffer.flip();

                                                    charBuffer = decoder.decode(buffer);
                                                    println(charBuffer.toString());

                                                    if (buffer.hasRemaining()) {
                                                        buffer.compact();
                                                    } else {
                                                        buffer.clear();
                                                    }

                                                    val r: Int = Random().nextInt(100);
                                                    if (r == 50) {
                                                        println("50 was generated! close the socket channel!");
                                                        break;
                                                    } else {
                                                        randomBuffer = ByteBuffer.wrap("Random number: $r".toByteArray(Charset.forName("UTF-8")));
                                                        keySocketChannel.write(randomBuffer);
                                                    }
                                                }
                                            }
                                        } catch (e: Exception) {
                                            System.err.println(e);
                                        }
                                    }
                                }
                            } else {
                                println("The socket channel or selector cannot be opened");
                            }
                        }
                }
        } catch (e: Exception) {
            System.err.println(e);
        }
    }
}