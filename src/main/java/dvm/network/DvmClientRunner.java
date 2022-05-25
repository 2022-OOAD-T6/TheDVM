package dvm.network;

import DVM_Client.DVMClient;
import GsonConverter.Serializer;
import Model.Message;

import java.util.logging.Logger;

public class DvmClientRunner implements Runnable {

    private final String host;
    private final Message message;
    private final Serializer serializer = new Serializer();


    public DvmClientRunner(String host, Message message) {
        this.host = host;
        this.message = message;
    }

    @Override
    public void run() {
        String jsonMessage = serializer.message2Json(message);
        DVMClient client = new DVMClient(host, jsonMessage);
        try{
            client.run();
            Logger.getGlobal().info("메세지 전달 완료 | to " + message.getDstID() + " | " + message.getMsgType() + " | ");
        }catch (Exception e){
            e.printStackTrace();
            Logger.getGlobal().warning("메세지 전송 불가 | " + e.getMessage() + " | from " + message.getSrcId() + " to " + message.getDstID());
        }
    }
}
