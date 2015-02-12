package netlib.core.codec.common;

import java.awt.image.BufferedImage;
import netlib.core.buffer.Buffer;
import netlib.core.buffer.BufferBuilder;
import netlib.core.codec.Codec;

public class ImageCodec extends Codec<BufferedImage> {

    public static final short ID = -11;

    public ImageCodec(){
        super(ID, BufferedImage.class);
    }

    public void encode(final BufferBuilder bldr, final BufferedImage img){
        final short width = (short)img.getWidth();
        final short height = (short)img.getHeight();
        bldr.putShort(width);
        bldr.putShort(height);
        bldr.putByte((byte)img.getType());
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
                bldr.putInt(img.getRGB(x, y));
    }

    public BufferedImage decode(final Buffer buffer){
        final short width = buffer.getShort();
        final short height = buffer.getShort();
        final BufferedImage img = new BufferedImage(width, height, buffer.getByte());
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
                img.setRGB(x, y, buffer.getInt());
        return img;
    }
}
