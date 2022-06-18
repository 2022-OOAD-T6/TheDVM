package dvm.network;

import Model.Message;
import dvm.service.ItemService;
import dvm.service.NetworkService;
import dvm.service.PrepaymentService;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 * Receiver 구현 클래스
 * DvmServer 라이브러리 없이 서버를 직접 구현한 버전
 */
public class ServerSocketReceiver implements Receiver {

    private static final int port = 8080; // 항상 8080

    private final Vector<Message> responseMessages;

    private final ItemService itemService;

    private final PrepaymentService prepaymentService;

    private final NetworkService networkService;

    private MessageType waitingMessageType;

    public ServerSocketReceiver(ItemService itemService, PrepaymentService prepaymentService, NetworkService networkService) {
        this.itemService = itemService;
        this.prepaymentService = prepaymentService;
        this.networkService = networkService;
        this.responseMessages = new Vector<>();
        this.waitingMessageType = MessageType.NONE;
    }

    @Override
    public void run() {
        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                Socket socket = serverSocket.accept();
                ReceiveMessageHandler handler = new ReceiveMessageHandler(socket, waitingMessageType, responseMessages, itemService, prepaymentService, networkService);
                new Thread(handler).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void changeWaitingMessageType(MessageType messageType) {
        this.waitingMessageType = messageType;
        if (messageType == MessageType.NONE) {
            System.out.println("receiver 상태 수정 | 기다리는 메세지가 없습니다. ");
        } else {
            System.out.println("receiver 상태 수정 | " + waitingMessageType.getTypeName() + "을 기다리는 중");
        }
    }

    @Override
    public Vector<Message> getResponseMessages() {
        return new Vector<>(responseMessages);
    }

    @Override
    public void clearResponseMessages() {
        responseMessages.clear();
    }

}