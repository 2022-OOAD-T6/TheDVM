package dvm.network;

import DVM_Server.DVMServer;
import Model.Message;
import dvm.service.ItemService;
import dvm.service.PrepaymentService;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 */
public class Receiver implements Runnable {

    private static final int port = 8080; // 항상 8080

    private final Vector<Message> responseMessages;

    private final ItemService itemService;

    private final PrepaymentService prepaymentService;

    private final NetworkService networkService;

    private MessageType waitingMessageType;

    public Receiver(ItemService itemService, PrepaymentService prepaymentService, NetworkService networkService) {
        this.itemService = itemService;
        this.prepaymentService = prepaymentService;
        this.networkService = networkService;
        this.responseMessages = new Vector<>();
        this.waitingMessageType = MessageType.NONE;
    }

    @Override
    public void run() {
        while(true){
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                Socket socket = serverSocket.accept();
                ReceiveMessageHandler handler = new ReceiveMessageHandler(socket, waitingMessageType, responseMessages, itemService, prepaymentService, networkService);
                new Thread(handler).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void changeWaitingMessageType(MessageType messageType) {
        this.waitingMessageType = messageType;
        if(messageType == MessageType.NONE){
            System.out.println("receiver 상태 수정 | 기다리는 메세지가 없습니다. ");
        }else{
            System.out.println("receiver 상태 수정 | " + waitingMessageType.getTypeName() + "을 기다리는 중");
        }
    }

    public Vector<Message> getResponseMessages() {
        return responseMessages;
    }

    public void clearResponseMessages() {
        responseMessages.clear();
    }

}