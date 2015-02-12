package netlib.server.connection;

import java.io.DataInputStream;
import java.io.EOFException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import netlib.core.buffer.Buffer;
import netlib.core.buffer.BufferBuilder;
import netlib.core.data.Data;
import netlib.server.Server;

public class NonBlockingConnection extends Connection{

    private final SocketChannel channel;
    private final DataInputStream input;

    public NonBlockingConnection(final Server server, final SocketChannel channel, final Socket socket) throws Exception{
        super(server, socket);
        this.channel = channel;

        input = new DataInputStream(socket.getInputStream());
    }

    public void close() throws Exception{
        input.close();
        channel.close();
        super.close();
    }

    public boolean isConnected(){
        return channel.isConnected() && super.isConnected();
    }

    public Data read() throws Exception{
        final int length = input.readInt();
        if(length < 0)
            throw new EOFException("Stream closed");
        final byte[] bytes = new byte[length];
        input.readFully(bytes);
        return Data.decode(new Buffer(bytes));
    }

    public void send(final Data data) throws Exception{
        final Buffer buf = new BufferBuilder().putBuffer(data.toBuffer()).build();
        final ByteBuffer buffer = ByteBuffer.wrap(buf.getAllBytes());
        while(buffer.hasRemaining())
            channel.write(buffer);
    }
}
