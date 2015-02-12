package netlib.server;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import netlib.core.connect.Connectable;
import netlib.core.data.Data;
import netlib.core.task.Task;
import netlib.core.task.TaskSystem;
import netlib.server.connection.Connection;
import netlib.server.event.ServerListener;
import netlib.server.handler.DefaultServerDataHandler;
import netlib.server.handler.ServerDataHandler;

public abstract class Server extends Connectable{

    private final ServerImpl impl;

    private final List<Connection> connections;

    private final List<ServerListener> listeners;
    private final Map<Integer, ServerDataHandler> handlers;

    private ServerDataHandler defaultHandler;

    public Server(final InetSocketAddress address, final boolean blocking){
        super(address);
        impl = blocking ? new BlockingServerImpl(this) : new NonBlockingServerImpl(this);

        defaultHandler = new DefaultServerDataHandler();

        connections = new ArrayList<>();

        handlers = new HashMap<>();

        listeners = new ArrayList<>();
    }

    public void addListener(final ServerListener l){
        listeners.add(l);
    }

    public void removeListener(final ServerListener l){
        listeners.remove(l);
    }

    public ServerListener[] getListeners(){
        return listeners.toArray(new ServerListener[listeners.size()]);
    }

    public void register(final ServerDataHandler... handlers){
        for(final ServerDataHandler handler : handlers)
            for(final int opcode : handler.getOpcodes())
                this.handlers.put(opcode, handler);
    }

    public ServerDataHandler getHandler(final int opcode){
        return handlers.get(opcode);
    }

    public void setDefaultHandler(final ServerDataHandler handler){
        defaultHandler = handler;
    }

    public ServerDataHandler getDefaultHandler(){
        return defaultHandler;
    }

    void join(final Connection con){
        if(!connections.add(con))
            return;
        for(final ServerListener l : listeners)
            l.onJoin(this, con);
    }

    void leave(final Connection con){
        if(!connections.remove(con))
            return;
        for(final ServerListener l : listeners)
            l.onLeave(this, con);
    }

    void handleData(final Connection con, final Data data){
        final ServerDataHandler handler = getHandler(data.getOpcode());
        handle(handler != null ? handler : defaultHandler, con, data);
    }

    private void handle(final ServerDataHandler handler, final Connection con, final Data data){
        if(handler == null)
            return;
        try{
            handler.handle(this, con, data);
        }catch(Exception ex){
            handler.handleException(this, con, data, ex);
        }
    }

    public Connection[] getConnections(){
        return connections.toArray(new Connection[connections.size()]);
    }

    public void sendToAll(final Data data) throws Exception{
        for(final Connection con : connections)
            con.send(data);
    }

    public void sendToAllExcept(final Data data, final Connection... exceptions) throws Exception{
        final List<Connection> ex = Arrays.asList(exceptions);
        for(final Connection con : connections)
            if(!ex.contains(con))
                con.send(data);
    }

    public InetSocketAddress getAddress(){
        return address;
    }

    public boolean isConnected(){
        return impl.isConnected();
    }

    public void start() throws Exception{
        init();
        impl.init();
        for(final ServerListener l : listeners)
            l.onStart(this);
        final Thread t = new Thread(
                new Runnable(){
                    public void run(){
                        while(isConnected()){
                            try{
                                if(!impl.loop())
                                    break;
                            }catch(Exception ex){
                                ex.printStackTrace();
                            }
                        }
                        try{
                            close();
                        }catch(Exception ex){
                            ex.printStackTrace();
                        }
                        for(final ServerListener l : listeners)
                            l.onFinish(Server.this);
                    }
                }
        );
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }

    public void close() throws Exception{
        taskSystem.shutdown();
        impl.close();
    }

    public String toString(){
        return address.toString();
    }
}
