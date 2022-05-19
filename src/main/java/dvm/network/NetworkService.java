package dvm.network;

import dvm.network.message.*;

import java.util.*;

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

    public Vector<SaleResponseMessage> getSaleResponseMessages() {
        receiver.changeWaitingMessageType(MessageType.NONE);
        Vector<Message> messages = receiver.getResponseMessages();
        Vector<SaleResponseMessage> saleResponseMessages = new Vector<>();
        for (Message message : messages) {
            if (message instanceof SaleResponseMessage) {
                saleResponseMessages.add((SaleResponseMessage) message);
            }
        }
        Collections.sort(saleResponseMessages);
        return saleResponseMessages;
    }

    public Vector<StockResponseMessage> getStockResponseMessages() {
        receiver.changeWaitingMessageType(MessageType.NONE);
        Vector<Message> messages = receiver.getResponseMessages();
        Vector<StockResponseMessage> stockResponseMessage = new Vector<>();
        for (Message message : messages) {
            if (message instanceof StockResponseMessage) {
                stockResponseMessage.add((StockResponseMessage) message);
            }
        }
        Collections.sort(stockResponseMessage);
        return stockResponseMessage;
    }

    public void clearResponseMessages() {
        receiver.clearResponseMessages();
    }
}