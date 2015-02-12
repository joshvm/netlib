package netlib.core.codec.common.primitive;

import netlib.core.buffer.Buffer;
import netlib.core.buffer.BufferBuilder;
import netlib.core.codec.Codec;

public class ShortCodec extends Codec<Short> {

    public static final short ID = -5;

    public ShortCodec(){
        super(ID, Short.class);
    }

    public void encode(final BufferBuilder bldr, final Short s){
        bldr.putShort(s);
    }

    public Short decode(final Buffer buffer){
        return buffer.getShort();
    }
}
