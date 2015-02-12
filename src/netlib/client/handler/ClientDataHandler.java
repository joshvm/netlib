package netlib.client.handler;

import netlib.client.Client;
import netlib.core.data.Data;
import netlib.core.data.handler.DataHandler;

public abstract class ClientDataHandler<T extends Client> implements DataHandler{

    public abstract void handle(final T client, final Data data) throws Exception;

    public void handleException(final T client, final Data data, final Exception ex){
        ex.printStackTrace();
    }

}
