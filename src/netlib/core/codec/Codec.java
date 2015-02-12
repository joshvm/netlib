package netlib.core.codec;

import netlib.core.buffer.Buffer;
import netlib.core.buffer.BufferBuilder;

public abstract class Codec<T> {

    private final short id;
    private final Class<T> clazz;

    public Codec(final short id, final Class<T> clazz){
        this.id = id;
        this.clazz = clazz;
    }

    public Class<T> getTypeClass(){
        return clazz;
    }

    public short getId(){
        return id;
    }

    public abstract void encode(final BufferBuilder bldr, final T obj);

    public abstract T decode(final Buffer buffer);
}
