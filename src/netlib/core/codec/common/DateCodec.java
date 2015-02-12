package netlib.core.codec.common;

import java.util.Date;
import netlib.core.buffer.Buffer;
import netlib.core.buffer.BufferBuilder;
import netlib.core.codec.Codec;

public class DateCodec extends Codec<Date> {

    public static final short ID = -10;

    public DateCodec(){
        super(ID, Date.class);
    }

    public void encode(final BufferBuilder bldr, final Date date){
        bldr.putLong(date.getTime());
    }

    public Date decode(final Buffer buffer){
        return new Date(buffer.getLong());
    }
}
