package netlib.core.codec.common;

import java.awt.Rectangle;
import netlib.core.buffer.Buffer;
import netlib.core.buffer.BufferBuilder;
import netlib.core.codec.Codec;

public class RectangleCodec extends Codec<Rectangle> {

    public static final short ID = -14;

    public RectangleCodec(){
        super(ID, Rectangle.class);
    }

    public void encode(final BufferBuilder bldr, final Rectangle rect){
        bldr.putInt(rect.x);
        bldr.putInt(rect.y);
        bldr.putInt(rect.width);
        bldr.putInt(rect.height);
    }

    public Rectangle decode(final Buffer buffer){
        return new Rectangle(buffer.getInt(), buffer.getInt(), buffer.getInt(), buffer.getInt());
    }
}
