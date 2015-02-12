package netlib.core.codec.common.primitive;

import netlib.core.buffer.Buffer;
import netlib.core.buffer.BufferBuilder;
import netlib.core.codec.Codec;

public class IntegerCodec extends Codec<Integer> {

    public static final short ID = -6;

    public IntegerCodec(){
        super(ID, Integer.class);
    }

    public void encode(final BufferBuilder bldr, final Integer i){
        bldr.putInt(i);
    }

    public Integer decode(final Buffer buffer){
        return buffer.getInt();
    }
}
