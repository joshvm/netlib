package netlib.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import netlib.client.event.ClientListener;
import netlib.client.handler.ClientDataHandler;
import netlib.client.handler.DefaultClientDataHandler;
import netlib.core.buffer.Buffer;
import netlib.core.connect.Connectable;
import netlib.core.data.Data;

public abstract class Client extends Connectable{

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    private final List<ClientListener> listeners;

    private ClientDataHandler defaultHandler;

    private final Map<Integer, ClientDataHandler> handlers;

    public Client(final InetSocketAddress address){
        super(address);

        listeners = new ArrayList<>();

        defaultHandler = new DefaultClientDataHandler();

        handlers = new HashMap<>();
    }

    public void register(final ClientDataHandler... handlers){
        for(final ClientDataHandler handler : handlers)
            for(final int opcode : handler.getOpcodes())
                this.handlers.put(opcode, handler);
    }

    public ClientDataHandler getHandler(final int opcode){
        return handlers.get(opcode);
    }

    public void setDefaultHandler(final ClientDataHandler handler){
        defaultHandler = handler;
    }

    public ClientDataHandler getDefaultHandler(){
        return defaultHandler;
    }

    public void addListener(final ClientListener l){
        listeners.add(l);
    }

    public void removeListener(final ClientListener l){
        listeners.remove(l);
    }

    public ClientListener[] getListeners(){
        return listeners.toArray(new ClientListener[listeners.size()]);
    }

    public void start() throws Exception{
        socket = new Socket();
        socket.connect(address);

        output = new DataOutputStream(socket.getOutputStream());
        output.flush();

        input = new DataInputStream(socket.getInputStream());

        for(final ClientListener l : listeners)
            l.onStart(this);

        final Thread t = new Thread(
                new Runnable(){
                    public void run(){
                        while(isConnected()){
                            try{
                                final Data data = read();
                                final ClientDataHandler handler = getHandler(data.getOpcode());
                                handle(handler != null ? handler : defaultHandler, data);
                            }catch(Exception ex){
                                try{
                                    close();
                                }catch(Exception e){}
                                break;
                            }
                        }
                        for(final ClientListener l : listeners)
                            l.onFinish(Client.this);
                    }
                }
        );
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }

    private void handle(final ClientDataHandler handler, final Data data){
        if(handler == null)
            return;
        try{
            handler.handle(this, data);
        }catch(Exception ex){
            handler.handleException(this, data, ex);
        }
    }

    private Data read() throws Exception{
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

    public boolean isConnected(){
        return socket.isConnected();
    }

    public void close() throws Exception{
        taskSystem.shutdown();
        output.close();
        input.close();
        socket.close();
    }
}
