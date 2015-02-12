package netlib.client.handler;

import netlib.client.Client;
import netlib.core.data.Data;

public class DefaultClientDataHandler extends ClientDataHandler{

    public void handle(final Client client, final Data data){
        System.out.printf("[%s] %s", client, data);
    }

    public int[] getOpcodes(){
        return null;
    }
}
