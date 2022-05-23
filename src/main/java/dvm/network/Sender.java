package dvm.network;

import DVM_Client.DVMClient;
import GsonConverter.Serializer;
import Model.Message;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 *
 */
public class Sender implements  Runnable{

    private final HashMap<String, String> dvmsNetworkInfo = new HashMap<>();

    // Message to jsonString
    private final Serializer serializer = new Serializer();

    private final static Logger logger = Logger.getGlobal();

    private final Message message;

    public Sender(Message message) {
        //dvmsNetworkInfo.put("Team0", "192.168.67.7");
        //dvmsNetworkInfo.put("Team2", "192.168.67.7");
        //dvmsNetworkInfo.put("Team3", "192.168.64.1");

        dvmsNetworkInfo.put("Team1", "192.168.66.43");
        //dvmsNetworkInfo.put("Team2", "192.168.64.202");
        dvmsNetworkInfo.put("Team3", "192.168.64.242");
//        dvmsNetworkInfo.put("Team4", "192.168.67.39");
        this.message = message;
        // dvmsNetworkInfo.put("Team5", "192.168.67.7");
        //dvmsNetworkInfo.put("Team6", "127.0.0.1"); // Our dvm
    }

    public void send(Message message) {
        try {
            if(message.getDstID().equals("0")){
                for (String teamId : dvmsNetworkInfo.keySet()) {
                    System.out.println("전송 중: " + teamId);
                    if(teamId.equals(message.getSrcId())) continue;
                    String dstIp = dvmsNetworkInfo.get(teamId);
                    String jsonMessage = serializer.message2Json(message);
                    // DVMClient client = new DVMClient(dstIp, jsonMessage);
                    // client.run();
                    Socket socket = new Socket(dstIp, 8080);
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(jsonMessage);
                    System.out.println("메세지 전달 완료 | to " + message.getDstID()+ " | "+message.getMsgType()+" | ");
                }
            }else {
                String dstIp = dvmsNetworkInfo.get(message.getDstID());
                String jsonMessage = serializer.message2Json(message);
                // DVMClient client = new DVMClient(dstIp, jsonMessage);
                // client.run();
                Socket socket = new Socket(dstIp, 8080);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(jsonMessage);
                System.out.println("메세지 전달 완료 | to " + message.getDstID()+ " | "+message.getMsgType()+" | ");
            }
        } catch (Exception e) {
            logger.warning("전송 불가 | " + e.getMessage() + "|" + message.getSrcId() + " to " + message.getDstID());
        }
    }

    public String getNetworkInfo(String id) {
        return dvmsNetworkInfo.get(id);
    }

    @Override
    public void run() {
        send(message);
    }
}