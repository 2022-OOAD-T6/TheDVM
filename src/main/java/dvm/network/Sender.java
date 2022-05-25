package dvm.network;

import DVM_Client.DVMClient;
import GsonConverter.Serializer;
import Model.Message;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 *
 */
public class Sender implements Runnable {

    private static final HashMap<String, String> dvmsNetworkInfo = new HashMap<>();

    // Message to jsonString
    private final Serializer serializer = new Serializer();

    private final static Logger logger = Logger.getGlobal();

    private final Message message;

    public Sender(Message message) {
        this.message = message;
    }

    public void send(Message message) {
        try {
            if (message.getDstID().equals("0")) {
                for (String teamId : dvmsNetworkInfo.keySet()) {
                    logger.info("전송 시도 | to " + teamId);
                    if (teamId.equals(message.getSrcId())) continue;
                    String dstIp = dvmsNetworkInfo.get(teamId);
                    DvmClientRunner dvmClientRunner = new DvmClientRunner(dstIp, message);
                    new Thread(dvmClientRunner).start();
                }
            } else {
                String dstIp = dvmsNetworkInfo.get(message.getDstID());
                DvmClientRunner dvmClientRunner = new DvmClientRunner(dstIp, message);
                new Thread(dvmClientRunner).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        send(message);
    }

    public static void initDvmsNetworkInfo(String[] ids, String[] ips) {
        for (int i = 0; i < ids.length; i++) {
            dvmsNetworkInfo.put(ids[i], ips[i]);
        }
    }

    public static void printDvmsNetworkInfo() {
        for (String s : dvmsNetworkInfo.keySet()) {
            System.out.println("team id: " + s + " | " + dvmsNetworkInfo.get(s));
        }
    }
}