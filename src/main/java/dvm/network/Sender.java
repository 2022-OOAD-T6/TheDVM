package dvm.network;

import dvm.network.message.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 */
public class Sender {

    private final HashMap<String, NetworkInfo> dvmNetworkInfo = new HashMap<>();

    public Sender() {
        dvmNetworkInfo.put("Team1", new NetworkInfo("127.0.0.1", 1001));
        dvmNetworkInfo.put("Team2", new NetworkInfo("127.0.0.1", 1002));
        dvmNetworkInfo.put("Team3", new NetworkInfo("127.0.0.1", 1003));
        dvmNetworkInfo.put("Team4", new NetworkInfo("127.0.0.1", 1004));
        dvmNetworkInfo.put("Team5", new NetworkInfo("127.0.0.1", 1005));
        dvmNetworkInfo.put("Team6", new NetworkInfo("127.0.0.1", 1006)); // Our dvm
    }

    public void send(Message message) {
        try {
            NetworkInfo dstNetworkInfo = dvmNetworkInfo.get(message.getDstId());
            Socket socket = new Socket(dstNetworkInfo.getIp(), dstNetworkInfo.getPort());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(message);
        } catch (Exception e) {
            System.out.println(message.getSrcId()+" to "+ message.getDstId() + ": 전송 불가");
        }
    }

    public NetworkInfo getNetworkInfo(String id){
        return dvmNetworkInfo.get(id);
    }

}