package netty.factorial.codecs.encoders

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import java.math.BigInteger

/**
 * Encodes a {@link Number} into the binary representation prepended with
 * a magic number ('F' or 0x46) and a 32-bit length prefix. For example, 42
 * will be encoded to {'F', 0, 0, 0, 1, 42 }.
 */
class NumberEncoder : MessageToByteEncoder<Number>() {
    override fun encode(ctx: ChannelHandlerContext?, msg: Number?, out: ByteBuf?) {
        // convert to a BigInteger first for easier implementation
        var v: BigInteger;
        if (msg is BigInteger) {
            v = msg;
        }
        else {
            v = BigInteger(msg.toString());
        }

        // convert the number into a byte array.
        var data: ByteArray = v.toByteArray();
        var dataLength: Int = data.size;

        // write a message.
        if (out != null) {
            with(out) {
                writeByte('F'.toInt()); // magic number
                writeInt(dataLength); // data length
                writeBytes(data); // data
            }
        }
    }
}

