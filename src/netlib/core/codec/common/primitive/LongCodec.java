package netlib.core.codec.common.primitive;

import netlib.core.buffer.Buffer;
import netlib.core.buffer.BufferBuilder;
import netlib.core.codec.Codec;

public class LongCodec extends Codec<Long> {

    public static final short ID = -8;

    public LongCodec(){
        super(ID, Long.class);
    }

    public void encode(final BufferBuilder bldr, final Long l){
        bldr.putLong(l);
    }

    public Long decode(final Buffer buffer){
        return buffer.getLong();
    }
}
