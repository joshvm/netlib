package netlib.core.codec.common.primitive;

import netlib.core.buffer.Buffer;
import netlib.core.buffer.BufferBuilder;
import netlib.core.codec.Codec;

public class StringCodec extends Codec<String> {

    public static final short ID = -15;

    public StringCodec(){
        super(ID, String.class);
    }

    public void encode(final BufferBuilder bldr, final String str){
        bldr.putString(str);
    }

    public String decode(final Buffer buffer){
        return buffer.getString();
    }
}
