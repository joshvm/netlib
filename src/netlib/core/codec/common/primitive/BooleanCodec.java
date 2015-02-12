package netlib.core.codec.common.primitive;

import netlib.core.buffer.Buffer;
import netlib.core.buffer.BufferBuilder;
import netlib.core.codec.Codec;

public class BooleanCodec extends Codec<Boolean> {

    public static final short ID = -1;

    public BooleanCodec(){
        super(ID, Boolean.class);
    }

    public void encode(final BufferBuilder bldr, final Boolean b){
        bldr.putBoolean(b);
    }

    public Boolean decode(final Buffer buffer){
        return buffer.getBoolean();
    }
}
