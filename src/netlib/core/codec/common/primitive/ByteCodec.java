package netlib.core.codec.common.primitive;

import netlib.core.buffer.Buffer;
import netlib.core.buffer.BufferBuilder;
import netlib.core.codec.Codec;

public class ByteCodec extends Codec<Byte> {

    public static final short ID = -2;

    public ByteCodec(){
        super(ID, Byte.class);
    }

    public void encode(final BufferBuilder bldr, final Byte b){
        bldr.putByte(b);
    }

    public Byte decode(final Buffer buffer){
        return buffer.getByte();
    }
}
