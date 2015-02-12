package netlib.server.event;

import netlib.server.Server;
import netlib.server.connection.Connection;

public interface ServerListener<T extends Server> {

    public void onStart(final T server);

    public void onFinish(final T server);

    public void onJoin(final T server, final Connection con);

    public void onLeave(final T server, final Connection con);
}
