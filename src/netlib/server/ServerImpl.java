package netlib.server;

interface ServerImpl {

    void init() throws Exception;

    boolean isConnected();

    boolean loop() throws Exception;

    void close() throws Exception;

}
