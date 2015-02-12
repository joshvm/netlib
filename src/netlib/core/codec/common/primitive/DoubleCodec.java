package netlib.core.codec.common.primitive;

import netlib.core.buffer.Buffer;
import netlib.core.buffer.BufferBuilder;
import netlib.core.codec.Codec;

public class DoubleCodec extends Codec<Double> {

    public static final short ID = -9;

    public DoubleCodec(){
        super(ID, Double.class);
    }

    public void encode(final BufferBuilder bldr, final Double d){
        bldr.putDouble(d);
    }

    public Double decode(final Buffer buffer){
        return buffer.getDouble();
    }
}
