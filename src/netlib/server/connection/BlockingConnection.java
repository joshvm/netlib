package netlib.server.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import netlib.core.buffer.Buffer;
import netlib.core.data.Data;
import netlib.server.Server;

public class BlockingConnection extends Connection{

    private final DataInputStream input;
    private final DataOutputStream output;

    public BlockingConnection(final Server server, final Socket socket) throws Exception{
        super(server, socket);

        output = new DataOutputStream(socket.getOutputStream());
        output.flush();

        input = new DataInputStream(socket.getInputStream());
    }

    public void close() throws Exception{
        output.close();
        input.close();
        super.close();
    }

    public Data read() throws Exception{
        final int length = input.readInt();
        final byte[] bytes = new byte[length];
        input.readFully(bytes);
        return Data.decode(new Buffer(bytes));
    }

    public void send(final Data data) throws Exception{
        final byte[] buffer = data.toBuffer().getAllBytes();
        output.writeInt(buffer.length);
        output.write(buffer);
        output.flush();
    }
}
