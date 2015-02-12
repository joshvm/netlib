package netlib.core.buffer;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class Buffer {

    private final byte[] bytes;
    private final DataInputStream in;

    public Buffer(final byte[] bytes){
        this.bytes = bytes;

        in = new DataInputStream(new ByteArrayInputStream(bytes));
    }

    public Buffer close(){
        try{
            in.close();
        }catch(Exception ex){}
        return this;
    }

    public byte[] getAllBytes(){
        return bytes;
    }

    public boolean getBoolean(){
        try{
            return in.readBoolean();
        }catch(Exception ex){
            return false;
        }
    }

    public byte getByte(){
        try{
            return in.readByte();
        }catch(Exception ex){
            return -1;
        }
    }

    public byte[] getBytes(){
        try{
            final int length = in.readInt();
            final byte[] bytes = new byte[length];
            in.readFully(bytes);
            return bytes;
        }catch(Exception ex){
            return null;
        }
    }

    public char getChar(){
        try{
            return in.readChar();
        }catch(Exception ex){
            return 0;
        }
    }

    public short getShort(){
        try{
            return in.readShort();
        }catch(Exception ex){
            return -1;
        }
    }

    public int getInt(){
        try{
            return in.readInt();
        }catch(Exception ex){
            return -1;
        }
    }

    public float getFloat(){
        try{
            return in.readFloat();
        }catch(Exception ex){
            return -1;
        }
    }

    public long getLong(){
        try{
            return in.readLong();
        }catch(Exception ex){
            return -1;
        }
    }

    public double getDouble(){
        try{
            return in.readDouble();
        }catch(Exception ex){
            return -1;
        }
    }

    public String getString(){
        try{
            return in.readUTF();
        }catch(Exception ex){
            return null;
        }
    }
}
