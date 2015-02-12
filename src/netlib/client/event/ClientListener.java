package netlib.client.event;

import netlib.client.Client;

public interface ClientListener<T extends Client> {

    public void onStart(final T client);

    public void onFinish(final T client);
}
