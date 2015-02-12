package netlib.core.buffer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class BufferBuilder {

    private final ByteArrayOutputStream baos;
    private final DataOutputStream out;

    public BufferBuilder(){
        baos = new ByteArrayOutputStream();

        out = new DataOutputStream(baos);
    }

    public BufferBuilder close(){
        try{
            out.close();
        }catch(Exception ex){}
        return this;
    }

    public byte[] toByteArray(final boolean close){
        if(close)
            close();
        return baos.toByteArray();
    }

    public byte[] toByteArray(){
        return toByteArray(true);
    }

    public Buffer build(){
        return new Buffer(toByteArray());
    }

    public BufferBuilder putBuffer(final Buffer buffer){
        return putBytes(buffer.getBytes());
    }

    public BufferBuilder putBoolean(final boolean b){
        try{
            out.writeBoolean(b);
        }catch(Exception ex){}
        return this;
    }

    public BufferBuilder putByte(final byte b){
        try{
            out.write(b);
        }catch(Exception ex){}
        return this;
    }

    public BufferBuilder putBytes(final byte[] b){
        try{
            out.writeInt(b.length);
            out.write(b);
        }catch(Exception ex){}
        return this;
    }

    public BufferBuilder putChar(final char c){
        try{
            out.writeChar(c);
        }catch(Exception ex){}
        return this;
    }

    public BufferBuilder putShort(final short s){
        try{
            out.writeShort(s);
        }catch(Exception ex){}
        return this;
    }

    public BufferBuilder putInt(final int i){
        try{
            out.writeInt(i);
        }catch(Exception ex){}
        return this;
    }

    public BufferBuilder putFloat(final float f){
        try{
            out.writeFloat(f);
        }catch(Exception ex){}
        return this;
    }

    public BufferBuilder putLong(final long l){
        try{
            out.writeLong(l);
        }catch(Exception ex){}
        return this;
    }

    public BufferBuilder putDouble(final double d){
        try{
            out.writeDouble(d);
        }catch(Exception ex){}
        return this;
    }

    public BufferBuilder putString(final String s){
        try{
            out.writeUTF(s);
        }catch(Exception ex){}
        return this;
    }

}
