package dvm.network;

import dvm.network.message.Message;
import dvm.network.message.MessageType;
import dvm.service.ItemService;
import dvm.service.PrepaymentService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Vector;

/**
 * 
 */
public class ReceiveMessageHandler implements Runnable {

    private final Socket socket;

    private MessageType waitingMessageType;

    private final Vector<Message> responseMessages;

    private final ItemService itemService;

    private final PrepaymentService prepaymentService;


    public ReceiveMessageHandler(Socket socket, MessageType waitingMessageType, Vector<Message> responseMessages, ItemService itemService, PrepaymentService prepaymentService) {
        this.socket = socket;
        this.waitingMessageType = waitingMessageType;
        this.responseMessages = responseMessages;
        this.itemService = itemService;
        this.prepaymentService = prepaymentService;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("readLine = " + message);
                Message requestedMessage = MessageParser.createMessage(message);
                if(waitingMessageType == requestedMessage.getMessageType()){
                    responseMessages.add(requestedMessage);
                }else{
                    // TODO: 각 타입별로 응답
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}