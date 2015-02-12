package netlib.core.codec;

import java.util.HashMap;
import java.util.Map;
import netlib.core.codec.common.ByteArrayCodec;
import netlib.core.codec.common.ColorCodec;
import netlib.core.codec.common.DateCodec;
import netlib.core.codec.common.ImageCodec;
import netlib.core.codec.common.PointCodec;
import netlib.core.codec.common.RectangleCodec;
import netlib.core.codec.common.primitive.BooleanCodec;
import netlib.core.codec.common.primitive.ByteCodec;
import netlib.core.codec.common.primitive.CharacterCodec;
import netlib.core.codec.common.primitive.DoubleCodec;
import netlib.core.codec.common.primitive.FloatCodec;
import netlib.core.codec.common.primitive.IntegerCodec;
import netlib.core.codec.common.primitive.LongCodec;
import netlib.core.codec.common.primitive.ShortCodec;
import netlib.core.codec.common.primitive.StringCodec;

public final class Codecs {

    private static final Map<Short, Codec> MAP = new HashMap<>();
    private static final Map<Class, Codec> CLASSES = new HashMap<>();

    static{
        register(new BooleanCodec());
        register(new ByteCodec());
        register(new ByteArrayCodec());
        register(new CharacterCodec());
        register(new ShortCodec());
        register(new IntegerCodec());
        register(new FloatCodec());
        register(new LongCodec());
        register(new DoubleCodec());
        register(new StringCodec());
        register(new ColorCodec());
        register(new DateCodec());
        register(new ImageCodec());
        register(new PointCodec());
        register(new RectangleCodec());
    }

    private Codecs(){}

    public static boolean register(final Codec codec){
        return CLASSES.put(codec.getTypeClass(), codec) != null && MAP.put(codec.getId(), codec) != null;
    }

    public static boolean unregister(final Codec codec){
        return CLASSES.remove(codec.getTypeClass()) != null && MAP.remove(codec.getId()) != null;
    }

    public static Codec get(final short id){
        return MAP.get(id);
    }

    public static Codec get(final Class typeClass){
        return CLASSES.get(typeClass);
    }

    public static boolean contains(final short id){
        return MAP.containsKey(id);
    }

    public static boolean contains(final Codec codec){
        return contains(codec.getId());
    }
}
