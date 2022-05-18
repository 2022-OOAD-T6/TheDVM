package dvm.network;

import dvm.network.message.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 
 */
public class NetworkService {

    private final Sender sender;

    private final Receiver receiver;

    public NetworkService(Sender sender, Receiver receiver) {
        this.sender = sender;
        this.receiver = receiver;
        Thread serverThread = new Thread(receiver);
        serverThread.start();
        System.out.println("Run thread");
        // 현 DVM X,Y
        Message.setCurrentX(5);
        Message.setCurrentY(5);
    }

    public void sendStockRequestMessage(String itemCode, int quantity) {
        receiver.changeWaitingMessageType(MessageType.STOCK_RESPONSE);
        StockRequestMessage sendingMessage = new StockRequestMessage(itemCode, quantity);
        sender.send(sendingMessage);
    }

    public void sendStockResponseMessage(String dstId, String itemCode, int quantity) {
        // TODO: 클래스 다이어그램에 dstId 추가
        StockResponseMessage sendingMessage = new StockResponseMessage(dstId, itemCode, quantity);
        sender.send(sendingMessage);
    }

    public void sendPrepaymentInfoMessage(String dstId, String itemCode, int quantity, String verificationCode) {
        PrepaymentInfoMessage sendingMessage = new PrepaymentInfoMessage(dstId, itemCode, quantity, verificationCode);
        sender.send(sendingMessage);
    }

    public void sendSaleRequestMessage(String itemCode, int quantity) {
        receiver.changeWaitingMessageType(MessageType.SALE_RESPONSE);
        SaleRequestMessage sendingMessage = new SaleRequestMessage(itemCode, quantity);
        sender.send(sendingMessage);
    }

    public void sendSaleResponseMessage(String dstId, String itemCode) {
        SaleResponseMessage sendingMessage = new SaleResponseMessage(dstId, itemCode);
        sender.send(sendingMessage);
    }

    public Vector<Message> getSaleResponseMessages() {
        receiver.changeWaitingMessageType(MessageType.NONE);
        Vector<Message> receivedMessages = receiver.getResponseMessages();
        return receivedMessages;
    }

    public Vector<Message> getStockResponseMessages() {
        Vector<Message> receivedMessages = receiver.getResponseMessages();
        return receivedMessages;
    }

    public void clearResponseMessages(){
        receiver.clearResponseMessages();
    }
}