package netty.factorial.codecs.decoders

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import io.netty.handler.codec.CorruptedFrameException
import java.math.BigInteger

/**
 * Decodes the binary representation of {@link BigInteger} prepended
 * with a magic number ('F' or 0x46) and a 32-bit integer length prefix into a
 * {@link BigInteger} instance. For example, {'F', 0, 0, 0, 1, 42} will be
 * decoded into BigInteger("42").
 */
class BigIntegerDecoder : ByteToMessageDecoder() {
    override fun decode(ctx: ChannelHandlerContext?, inByteBuf: ByteBuf?, out: MutableList<Any>?) {
        // wait until the length prefix is available
        if (inByteBuf != null) {
            if (inByteBuf.readableBytes() < 5) {
                return;
            }

            inByteBuf.markReaderIndex();

            // check the magic number.
            var magicNumber: Int = inByteBuf.readUnsignedByte().toInt();
            if (magicNumber != 'F'.toInt()) {
                inByteBuf.resetReaderIndex();
                throw CorruptedFrameException("Invalid magic number: $magicNumber");
            }

            // wait until the whole data is available.
            var dataLength: Int = inByteBuf.readInt();
            if (inByteBuf.readableBytes() < dataLength) {
                inByteBuf.resetReaderIndex();
                return;
            }

            // convert the received data into a BigInteger.
            var decoded: ByteArray = ByteArray(dataLength);
            inByteBuf.readBytes(decoded);

            out?.add(BigInteger(decoded));
        }
    }
}

