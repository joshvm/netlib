package netlib.server.handler;

import netlib.core.data.Data;
import netlib.server.Server;
import netlib.server.connection.Connection;

public class DefaultServerDataHandler extends ServerDataHandler{

    public void handle(final Server server, final Connection con, final Data data){
        System.out.printf("[%s] %s\n", con, data);
    }

    public int[] getOpcodes(){
        return null;
    }
}
