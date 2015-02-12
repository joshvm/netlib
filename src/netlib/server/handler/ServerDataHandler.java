package netlib.server.handler;

import netlib.core.data.Data;
import netlib.core.data.handler.DataHandler;
import netlib.server.Server;
import netlib.server.connection.Connection;

public abstract class ServerDataHandler<T extends Server> implements DataHandler{

    public abstract void handle(final T server, final Connection connection, final Data data) throws Exception;

    public void handleException(final T server, final Connection connection, final Data data, final Exception ex){
        ex.printStackTrace();
    }
}
