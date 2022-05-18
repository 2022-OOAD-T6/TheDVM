package dvm.network;

import dvm.network.message.Message;
import dvm.network.message.MessageType;
import dvm.service.ItemService;
import dvm.service.PrepaymentService;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 * 
 */
public class Receiver implements Runnable {

    private final int port;

    private final Vector<Message> responseMessages;

    private final ItemService itemService;

    private final PrepaymentService prepaymentService;

    private MessageType waitingMessageType;

    public Receiver(int port, ItemService itemService, PrepaymentService prepaymentService) {
        this.port = port;
        this.itemService = itemService;
        this.prepaymentService = prepaymentService;

        this.responseMessages = new Vector<>();
        this.waitingMessageType = MessageType.NONE;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                ReceiveMessageHandler handler = new ReceiveMessageHandler(socket, waitingMessageType, responseMessages, itemService, prepaymentService);
                new Thread(handler).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeWaitingMessageType(MessageType messageType) {
        this.waitingMessageType = messageType;
    }

    public Vector<Message> getResponseMessages() {
        return responseMessages;
    }

    // TODO: 여기서 clear?
    public void clearResponseMessages(){
        responseMessages.clear();
    }

}