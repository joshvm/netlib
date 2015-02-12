package netlib.core.codec.common.primitive;

import netlib.core.buffer.Buffer;
import netlib.core.buffer.BufferBuilder;
import netlib.core.codec.Codec;

public class CharacterCodec extends Codec<Character> {

    public static final short ID = -4;

    public CharacterCodec(){
        super(ID, Character.class);
    }

    public void encode(final BufferBuilder bldr, final Character c){
        bldr.putChar(c);
    }

    public Character decode(final Buffer buffer){
        return buffer.getChar();
    }
}
