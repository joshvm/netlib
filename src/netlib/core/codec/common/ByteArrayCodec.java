package netlib.core.codec.common;

import netlib.core.buffer.Buffer;
import netlib.core.buffer.BufferBuilder;
import netlib.core.codec.Codec;

public class ByteArrayCodec extends Codec<byte[]> {

    public static final short ID = -3;

    public ByteArrayCodec(){
        super(ID, byte[].class);
    }

    public void encode(final BufferBuilder bldr, final byte[] b){
        bldr.putBytes(b);
    }

    public byte[] decode(final Buffer buffer){
        return buffer.getBytes();
    }
}
