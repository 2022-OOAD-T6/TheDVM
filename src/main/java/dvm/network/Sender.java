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
public class Sender {

    private final HashMap<String, String> dvmsNetworkInfo = new HashMap<>();

    // Message to jsonString
    private final Serializer serializer = new Serializer();

    private final static Logger logger = Logger.getGlobal();

    public Sender() {
        dvmsNetworkInfo.put("Team1", "127.0.0.1");
        dvmsNetworkInfo.put("Team2", "127.0.0.1");
        dvmsNetworkInfo.put("Team3", "127.0.0.1");
        dvmsNetworkInfo.put("Team4", "127.0.0.1");
        dvmsNetworkInfo.put("Team5", "127.0.0.1");
        dvmsNetworkInfo.put("Team6", "127.0.0.1"); // Our dvm
    }

    public void send(Message message) {
        try {
            if(message.getDstID().equals("0")){
                for (String teamId : dvmsNetworkInfo.keySet()) {
                    if(teamId.equals(message.getSrcId())) continue;
                    message.setDstID(teamId);
                    String dstIp = dvmsNetworkInfo.get(message.getDstID());
                    String jsonMessage = serializer.message2Json(message);
                    DVMClient client = new DVMClient(dstIp, jsonMessage);
                    client.run();
                    logger.info("메세지 전달 완료 | to " + message.getDstID()+ " | "+message.getMsgType()+" | ");
                }
            }else {
                String dstIp = dvmsNetworkInfo.get(message.getDstID());
                String jsonMessage = serializer.message2Json(message);
                DVMClient client = new DVMClient(dstIp, jsonMessage);
                client.run();
                logger.info("메세지 전달 완료 | to " + message.getDstID()+ " | "+message.getMsgType()+" | ");
            }
        } catch (Exception e) {
            logger.warning("전송 불가 | " + e.getMessage() + "|" + message.getSrcId() + " to " + message.getDstID());
        }
    }

    public String getNetworkInfo(String id) {
        return dvmsNetworkInfo.get(id);
    }
}