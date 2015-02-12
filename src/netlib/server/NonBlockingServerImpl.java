package netlib.server;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import netlib.core.data.Data;
import netlib.server.connection.NonBlockingConnection;

class NonBlockingServerImpl implements ServerImpl{

    private final Server server;

    private Selector selector;
    private ServerSocketChannel channel;

    NonBlockingServerImpl(final Server server){
        this.server = server;
    }

    public void init() throws Exception{
        selector = Selector.open();
        channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        channel.socket().bind(server.getAddress());
        channel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public boolean isConnected(){
        return channel.isOpen();
    }

    private void accept() throws Exception{
        final SocketChannel client = channel.accept();
        client.configureBlocking(false);
        final SelectionKey key = client.register(selector, SelectionKey.OP_READ);
        final NonBlockingConnection con = new NonBlockingConnection(server, client, client.socket());
        key.attach(con);
        server.join(con);
    }

    private void read(final NonBlockingConnection con) throws Exception{
        final Data data = con.read();
        server.handleData(con, data);
    }

    public boolean loop() throws Exception{
        selector.select();
        final Iterator<SelectionKey> itr = selector.selectedKeys().iterator();
        while(itr.hasNext()){
            final SelectionKey key = itr.next();
            itr.remove();
            if(key.isAcceptable()){
                try{
                    accept();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }else if(key.isReadable()){
                final NonBlockingConnection con = (NonBlockingConnection) key.attachment();
                try{
                    read(con);
                }catch(Exception ex){
                    ex.printStackTrace();
                    try{
                        con.close();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    server.leave(con);
                }
            }
        }
        return true;
    }

    public void close() throws Exception{
        channel.close();
    }
}
