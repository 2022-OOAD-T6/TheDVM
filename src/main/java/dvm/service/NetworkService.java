package dvm.service;

import GsonConverter.Serializer;
import Model.Message;
import dvm.network.*;

import java.util.*;
import java.util.logging.Logger;

import static dvm.network.MessageType.*;

/**
 * 네트워크 서비스 클래스
 */
public class NetworkService {

    //private final Sender sender;

    private final Receiver receiver;
    private String currentId;
    private final Logger logger = Logger.getGlobal();
    private final Serializer serializer = new Serializer();

    /*public NetworkService(String currentId, int currentX, int currentY, ItemService itemService, PrepaymentService prepaymentService) {
        //this.sender = new Sender();
        this.receiver = new Receiver(itemService, prepaymentService, this);
        currentId="Team3";

        currentId = "Team4";
        MessageFactory.setCurrentId(currentId);
        MessageFactory.setCurrentX(currentX);
        MessageFactory.setCurrentY(currentY);

        Thread serverThread = new Thread(receiver);
        serverThread.start();
        logger.info("Run thread");
    }*/

    public NetworkService(String currentId, int currentX, int currentY, ItemService itemService, PrepaymentService prepaymentService) {

        this.currentId = "Team4";
        MessageFactory.setCurrentId(this.currentId);
        MessageFactory.setCurrentX(currentX);
        MessageFactory.setCurrentY(currentY);

        // 리시버 구현체는 여기서 선택
        this.receiver = new MyReceiver(itemService, prepaymentService, this);
//        this.receiver = new ReceiverImpl(itemService, prepaymentService, this);
        new Thread(this.receiver).start();
    }

    public void sendStockRequestMessage(String itemCode, int quantity) {
        receiver.changeWaitingMessageType(STOCK_RESPONSE);
        Sender responseSender = new Sender(MessageFactory.createStockRequestMessage(itemCode, quantity));
        new Thread(responseSender).start();
        //responseSender.send(MessageFactory.createStockRequestMessage(itemCode, quantity));
    }

    public void sendStockResponseMessage(String dstId, String itemCode, int quantity) {
        Sender responseSender = new Sender(MessageFactory.createStockResponseMessage(dstId, itemCode, quantity));
        new Thread(responseSender).start();
        //responseSender.send(MessageFactory.createStockResponseMessage(dstId, itemCode, quantity));
    }

    public void sendPrepaymentInfoMessage(String dstId, String itemCode, int quantity, String verificationCode) {
        Sender responseSender = new Sender(MessageFactory.createPrepaymentCheckMessage(dstId, itemCode, quantity, verificationCode));
        new Thread(responseSender).start();
        //responseSender.send(MessageFactory.createPrepaymentCheckMessage(dstId, itemCode, quantity, verificationCode));
    }

    public void sendSaleRequestMessage(String itemCode, int quantity) {
        receiver.changeWaitingMessageType(SALE_RESPONSE);
        Sender responseSender = new Sender(MessageFactory.createSaleRequestMessage(itemCode, quantity));
        new Thread(responseSender).start();
        //responseSender.send(MessageFactory.createSaleRequestMessage(itemCode, quantity));
    }

    public void sendSaleResponseMessage(String dstId, String itemCode) {
        Sender responseSender = new Sender(MessageFactory.createSaleResponseMessage(dstId, itemCode));
        new Thread(responseSender).start();
        //responseSender.send(MessageFactory.createSaleResponseMessage(dstId, itemCode));
    }

    public Message getSaleResponseMessage(String itemCode) {
        Vector<Message> saleResponseMessages = getMessages(SALE_RESPONSE);
        clearResponseMessages();
        if(saleResponseMessages.isEmpty()){
            return null;
        }else{
            for (Message saleResponseMessage : saleResponseMessages) {
                if(saleResponseMessage.getMsgDescription().getItemCode().equals(itemCode)){
                    return saleResponseMessage;
                }
            }
            return null;
        }
    }

    public Message getStockResponseMessageFrom(String srcId, String itemCode, int quantity) {
        Vector<Message> stockResponseMessages = getMessages(STOCK_RESPONSE);
        clearResponseMessages();
        for(Message message : stockResponseMessages){
            if(message.getSrcId().equals(srcId) && message.getMsgDescription().getItemCode().equals(itemCode) && message.getMsgDescription().getItemNum() >= quantity){
                return message;
            }
        }
        return null;
    }

    private Vector<Message> getMessages(MessageType messageType){
        receiver.changeWaitingMessageType(NONE);
        Vector<Message> messages = receiver.getResponseMessages();
        logger.info("----------저장된 메세지------------");
        for (Message message : messages) {
            System.out.println(serializer.message2Json(message));
        }
        System.out.println("--------------------------------");

        Vector<Message> responseMessage = new Vector<>();
        for (Message message : messages) {
            if (message.getMsgType().equals(messageType.getTypeName())) {
                responseMessage.add(message);
            }
        }
        MessageFactory.sortMessages(responseMessage);
        return responseMessage;
    }

    public void changeWaitingMessageType(MessageType messageType){
        receiver.changeWaitingMessageType(messageType);
    }

    private void clearResponseMessages() {
        receiver.clearResponseMessages();
    }
}