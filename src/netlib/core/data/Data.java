package netlib.core.data;

import java.util.ArrayList;
import java.util.List;
import netlib.core.buffer.Buffer;
import netlib.core.buffer.BufferBuilder;
import netlib.core.codec.Codec;
import netlib.core.codec.Codecs;

public class Data {

    private static class Piece<T>{

        private final Codec<T> codec;
        private final T value;

        private Piece(final Codec<T> codec, final T value){
            this.codec = codec;
            this.value = value;
        }

        private Piece(final T value){
            this(Codecs.get(value.getClass()), value);
        }
    }

    private final int opcode;
    private final List<Piece> pieces;

    private int cursor;

    public Data(final int opcode){
        this.opcode = opcode;

        pieces = new ArrayList<>();
    }

    public int getOpcode(){
        return opcode;
    }

    public void copyTo(final Data other){
        other.pieces.addAll(pieces);
    }

    public Data put(final Piece piece){
        pieces.add(piece);
        return this;
    }

    public Data put(final Object value){
        return put(new Piece(value));
    }

    public int getCursor(){
        return cursor;
    }

    public void setCursor(final int pos){
        if(pos < 0 || pos > pieces.size()-1)
            throw new IllegalArgumentException("Cursor must be [0, pieceCount)");
        cursor = pos;
    }

    public <T> T get(){
        if(cursor > pieces.size() - 1)
            cursor = 0;
        return get(cursor++);
    }

    public <T> T get(final int i){
        return (T)pieces.get(i).value;
    }

    public Piece[] getPieces(){
        return pieces.toArray(new Piece[pieces.size()]);
    }

    public int getPieceCount(){
        return pieces.size();
    }

    public Buffer toBuffer(){
        final BufferBuilder bldr = new BufferBuilder();
        bldr.putInt(opcode);
        bldr.putInt(pieces.size());
        for(final Piece p : pieces){
            bldr.putShort(p.codec.getId());
            p.codec.encode(bldr, p.value);
        }
        return bldr.build();
    }

    public String toString(){
        final StringBuilder bldr = new StringBuilder();
        bldr.append("Opcode: ").append(opcode)
                .append(" | ")
                .append("Pieces: ").append(getPieceCount())
                .append(" = ").append(pieces);
            bldr.setLength(bldr.length() - 1);
        return bldr.toString();
    }

    public static Data decode(final Buffer buffer){
        final Data data = create(buffer.getInt());
        final int count = buffer.getInt();
        for(int i = 0; i < count; i++){
            final Codec codec = Codecs.get(buffer.getShort());
            data.put(new Piece(codec, codec.decode(buffer)));
        }
        return data;
    }

    public static Data create(final int opcode){
        return new Data(opcode);
    }
}
