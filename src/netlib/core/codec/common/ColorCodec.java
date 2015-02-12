package netlib.core.codec.common;

import java.awt.Color;
import netlib.core.buffer.Buffer;
import netlib.core.buffer.BufferBuilder;
import netlib.core.codec.Codec;

public class ColorCodec extends Codec<Color> {

    public static final short ID = -12;

    public ColorCodec(){
        super(ID, Color.class);
    }

    public void encode(final BufferBuilder bldr, final Color color){
        bldr.putInt(color.getRGB());
    }

    public Color decode(final Buffer buffer){
        return new Color(buffer.getInt());
    }
}
