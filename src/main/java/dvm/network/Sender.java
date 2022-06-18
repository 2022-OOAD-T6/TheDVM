package dvm.network;

import GsonConverter.Serializer;
import Model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 */
public class Sender {

    private static final HashMap<String, String> dvmsNetworkInfo = new HashMap<>();

    // Message to jsonString
    private final Serializer serializer = new Serializer();

    private final static Logger logger = Logger.getGlobal();

    public void send(Message message) {
        try {
            if (message.getDstID().equals("0")) {
                for (Map.Entry<String, String> dvmNetworkInfo : dvmsNetworkInfo.entrySet()) {
                    String teamId = dvmNetworkInfo.getKey();
                    String dstIp = dvmNetworkInfo.getValue();
                    logger.info("전송 시도 | to " + teamId);
                    if (teamId.equals(message.getSrcId())) continue;
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

    public static void initDvmsNetworkInfo(String[] ids, String[] ips) {
        for (int i = 0; i < ids.length; i++) {
            dvmsNetworkInfo.put(ids[i], ips[i]);
        }
    }

    public static void printDvmsNetworkInfo() {
        for (Map.Entry<String, String> dvmNetworkInfo : dvmsNetworkInfo.entrySet()) {
            logger.info("team id: " + dvmNetworkInfo.getKey() + " | " + dvmNetworkInfo.getValue());
        }
    }
}