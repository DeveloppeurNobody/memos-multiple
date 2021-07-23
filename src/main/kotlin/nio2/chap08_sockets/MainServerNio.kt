package nio2.chap08_sockets

import java.io.IOException
import java.lang.Exception
import java.net.InetSocketAddress
import java.net.StandardSocketOptions
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel
import java.nio.charset.Charset

object MainServerNio {

    var keepDataTrack: MutableMap<SocketChannel, MutableList<ByteArray>> = mutableMapOf();
    private var buffer: ByteBuffer = ByteBuffer.allocate(2 * 1024);
    const val DEFAULT_PORT: Int = 5555;

    @JvmStatic
    fun main(args: Array<String>) {
        // open Selector and ServerSocketChannel by calling the open() method
        try {
            Selector.open()
                .use { selector ->
                    ServerSocketChannel.open()
                        .use { serverSocketChannel ->

                            // check that both of them were successfully opened
                            if ( (serverSocketChannel.isOpen) && (selector.isOpen) ) {
                                // configure non-blocking mode
                                serverSocketChannel.configureBlocking(false);

                                // set some options
                                serverSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 256 * 1024);
                                serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);

                                // bind the server socket channel to port
                                serverSocketChannel.bind(InetSocketAddress(DEFAULT_PORT));

                                // register the current channel with the given selector
                                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

                                // display a waiting message while ... waiting
                                println("Waiting for connections...");

                                while (true) {
                                    // wait for incomming events
                                    selector.select();

                                    // there is something to process on selected keys
                                    var keys = selector.selectedKeys().iterator();

                                    while (keys.hasNext()) {
                                        val key: SelectionKey = keys.next() as SelectionKey;

                                        // prevent the same key from coming up again
                                        keys.remove();

                                        if (!key.isValid) {
                                            continue;
                                        }

                                        when {
                                            key.isAcceptable -> {
                                                acceptOP(key, selector);
                                            }
                                            key.isReadable -> {
                                                this.readOP(key);
                                            }
                                            key.isWritable -> {
                                                this.writeOP(key);
                                            }
                                        }
                                    }
                                }
                            } else {
                                println("The server socket channel or selector cannot be opened");
                            }
                        }
            }
        } catch (e: Exception) {
            System.err.println(e);
        }
    }

    // isAcceptable return true
    private fun acceptOP(key: SelectionKey, selector: Selector?) {
        var serverSocketChannel: ServerSocketChannel = key.channel() as ServerSocketChannel;
        var socketChannel: SocketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);

        println("Incoming connection from: ${socketChannel.remoteAddress}");

        // write an welcome message
        socketChannel.write(ByteBuffer.wrap("Hello".toByteArray(Charset.forName("UTF-8"))));

        // register channel with selector for further I/O
        keepDataTrack.put(socketChannel, mutableListOf<ByteArray>());
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    // isReadable returned true
    private fun readOP(key: SelectionKey) {
        try {
            var socketChannel: SocketChannel = key.channel() as SocketChannel;

            buffer.clear();

            var numRead = -1;

            try {
                numRead = socketChannel.read(buffer);

            } catch (ioe: IOException) {
                System.err.println(ioe);
            }

            if (numRead == -1) {
                this.keepDataTrack.remove(socketChannel);
                println("Connection closed by ${socketChannel.remoteAddress}");
                socketChannel.close();
                key.cancel();
                return;
            }

            var data: ByteArray = ByteArray(numRead);
            System.arraycopy(buffer.array(), 0, data, 0, numRead);
            println(String(data, Charset.forName("UTF-8")) + " from ${socketChannel.remoteAddress}");

            // write back to client
            doEchoJob(key, data);

        } catch (e: Exception) {
            System.err.println(e);
        }
    }

    private fun doEchoJob(key: SelectionKey, data: ByteArray) {
        val socketChannel: SocketChannel = key.channel() as SocketChannel;
        val channelData: MutableList<ByteArray>? = keepDataTrack.get(socketChannel);
        channelData?.add(data);

        key.interestOps(SelectionKey.OP_WRITE);
    }

    @Throws(IOException::class)
    private fun writeOP(key: SelectionKey) {
        val socketChannel: SocketChannel = key.channel() as SocketChannel;

        val channelData: MutableList<ByteArray>? = keepDataTrack.get(socketChannel);
        val its: MutableIterator<ByteArray>? = channelData?.iterator();

        while (its?.hasNext()!!) {
            val it: ByteArray = its.next();
            its.remove();
            socketChannel.write(ByteBuffer.wrap(it));
        }

        key.interestOps(SelectionKey.OP_READ);
    }




}