package netlib.core.connect;

import java.net.InetSocketAddress;
import netlib.core.task.Task;
import netlib.core.task.TaskSystem;

public abstract class Connectable {

    protected final InetSocketAddress address;
    protected final TaskSystem taskSystem;

    protected Connectable(final InetSocketAddress address){
        this.address = address;

        taskSystem = new TaskSystem();
    }

    public void submit(final Task task){
        taskSystem.submit(task);
    }

    public InetSocketAddress getAddress(){
        return address;
    }

    public String toString(){
        return address.toString();
    }

    public abstract void init();

    public abstract boolean isConnected();

    public abstract void close() throws Exception;
}
