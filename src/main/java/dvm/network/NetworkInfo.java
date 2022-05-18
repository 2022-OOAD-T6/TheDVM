package dvm.network;

/**
 * 
 */
public class NetworkInfo {

    private String ip;

    private String port;

    public NetworkInfo(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

}