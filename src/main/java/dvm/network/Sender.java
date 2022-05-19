package dvm.network;

import dvm.network.message.Message;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

/**
 *
 */
public class Sender {

    private final HashMap<String, NetworkInfo> dvmNetworkInfoMap = new HashMap<>();

    public Sender() {
        dvmNetworkInfoMap.put("Team1", new NetworkInfo("127.0.0.1", 1001));
        dvmNetworkInfoMap.put("Team2", new NetworkInfo("127.0.0.1", 1002));
        dvmNetworkInfoMap.put("Team3", new NetworkInfo("127.0.0.1", 1003));
        dvmNetworkInfoMap.put("Team4", new NetworkInfo("127.0.0.1", 1004));
        dvmNetworkInfoMap.put("Team5", new NetworkInfo("127.0.0.1", 1005));
        dvmNetworkInfoMap.put("Team6", new NetworkInfo("127.0.0.1", 1006)); // Our dvm
    }

    public void send(Message message) {
        try {
            if(message.getDstId().equals("0")){
                for (String teamId : dvmNetworkInfoMap.keySet()) {
                    message.setDstId(teamId);
                    NetworkInfo dstNetworkInfo = dvmNetworkInfoMap.get(teamId);
                    Socket socket = new Socket(dstNetworkInfo.getIp(), dstNetworkInfo.getPort());
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(message);
                    System.out.println("메세지 전달 완료 | " + message);
                }
            }else{
                NetworkInfo dstNetworkInfo = dvmNetworkInfoMap.get(message.getDstId());
                Socket socket = new Socket(dstNetworkInfo.getIp(), dstNetworkInfo.getPort());
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(message);
                System.out.println("메세지 전달 완료 | " + message);
            }
        } catch (Exception e) {
            System.out.println("전송 불가 | " + message.getSrcId() + " to " + message.getDstId());
        }
    }

    public NetworkInfo getNetworkInfo(String id) {
        return dvmNetworkInfoMap.get(id);
    }

}