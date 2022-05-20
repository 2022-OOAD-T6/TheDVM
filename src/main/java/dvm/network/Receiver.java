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

    private final NetworkService networkService;

    private MessageType waitingMessageType;

    public Receiver(int port, ItemService itemService, PrepaymentService prepaymentService, NetworkService networkService) {
        this.port = port;
        this.itemService = itemService;
        this.prepaymentService = prepaymentService;
        this.networkService = networkService;

        this.responseMessages = new Vector<>();
        this.waitingMessageType = MessageType.NONE;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                ReceiveMessageHandler handler = new ReceiveMessageHandler(socket, waitingMessageType, responseMessages, itemService, prepaymentService, networkService);
                new Thread(handler).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeWaitingMessageType(MessageType messageType) {
        this.waitingMessageType = messageType;
        System.out.println("receiver 상태 수정 | " + waitingMessageType.getTypeName() + "을 기다리는 중");
    }

    public Vector<Message> getResponseMessages() {
        return responseMessages;
    }

    public void clearResponseMessages() {
        responseMessages.clear();
    }

}