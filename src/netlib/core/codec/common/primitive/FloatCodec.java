package netlib.core.codec.common.primitive;

import netlib.core.buffer.Buffer;
import netlib.core.buffer.BufferBuilder;
import netlib.core.codec.Codec;

public class FloatCodec extends Codec<Float> {

    public static final short ID = -7;

    public FloatCodec(){
        super(ID, Float.class);
    }

    public void encode(final BufferBuilder bldr, final Float f){
        bldr.putFloat(f);
    }

    public Float decode(final Buffer buffer){
        return buffer.getFloat();
    }
}
