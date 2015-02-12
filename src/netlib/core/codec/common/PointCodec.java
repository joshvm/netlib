package netlib.core.codec.common;

import java.awt.Point;
import netlib.core.buffer.Buffer;
import netlib.core.buffer.BufferBuilder;
import netlib.core.codec.Codec;

public class PointCodec extends Codec<Point> {

    public static final short ID = -13;

    public PointCodec(){
        super(ID, Point.class);
    }

    public void encode(final BufferBuilder bldr, final Point point){
        bldr.putInt(point.x);
        bldr.putInt(point.y);
    }

    public Point decode(final Buffer buffer){
        return new Point(buffer.getInt(), buffer.getInt());
    }
}
