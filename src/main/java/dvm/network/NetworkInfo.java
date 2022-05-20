package dvm.network;

/**
 * 
 */
public class NetworkInfo {

    private final String ip;

    private final int port;

    public NetworkInfo(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

}