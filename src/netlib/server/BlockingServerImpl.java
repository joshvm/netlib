package netlib.server;

import java.net.ServerSocket;
import java.net.Socket;
import netlib.core.data.Data;
import netlib.server.connection.BlockingConnection;
import netlib.server.connection.Connection;

class BlockingServerImpl implements ServerImpl{

    private final Server server;
    private ServerSocket socket;

    BlockingServerImpl(final Server server){
        this.server = server;
    }

    public void init() throws Exception{
        socket = new ServerSocket();
        socket.bind(server.getAddress());
    }

    public boolean isConnected(){
        return socket.isBound();
    }

    public void close() throws Exception{
        socket.close();
    }

    public boolean loop() throws Exception{
        final Socket client = socket.accept();
        final Connection con = new BlockingConnection(server, client);
        server.join(con);
        final Thread t = new Thread(
                new Runnable(){
                    public void run(){
                        while(con.isConnected()){
                            try{
                                final Data data = con.read();
                                server.handleData(con, data);
                            }catch(Exception ex){
                                ex.printStackTrace();
                                try{
                                    con.close();
                                }catch(Exception ex2){}
                            }
                        }
                        server.leave(con);
                    }
                }
        );
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
        return true;
    }
}
