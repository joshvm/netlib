package netlib.server.connection;

import java.net.Socket;
import netlib.core.data.Data;
import netlib.server.Server;

public abstract class Connection {

    private Object attachment;

    protected final Server server;
    protected final Socket socket;

    Connection(final Server server, final Socket socket){
        this.server = server;
        this.socket = socket;
    }

    public Server getServer(){
        return server;
    }

    public Socket getSocket(){
        return socket;
    }

    public void attach(final Object obj){
        attachment = obj;
    }

    public <T> T attachment(){
        return (T)attachment;
    }

    public void close() throws Exception{
        socket.close();
    }

    public boolean isConnected(){
        return socket.isConnected();
    }

    public String toString(){
        return String.format("%s @ %s", server, socket);
    }

    public abstract Data read() throws Exception;

    public abstract void send(final Data data) throws Exception;
}
