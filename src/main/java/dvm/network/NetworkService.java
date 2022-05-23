package dvm.network;

import GsonConverter.Serializer;
import Model.Message;
import dvm.service.ItemService;
import dvm.service.PrepaymentService;

import java.util.*;
import java.util.logging.Logger;

import static dvm.network.MessageType.*;

/**
 *
 */
public class NetworkService {

    //private final Sender sender;

    // 임시로 지워봄
//    private final Receiver receiver;
    private final MyReceiver receiver;
    private final MyReceiverHandler myReceiverHandler;
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
        logger.info("network service start");
        this.receiver = new MyReceiver();
        new Thread(this.receiver).start();
        logger.info("network service fin");

        this.currentId = "Team4";
        MessageFactory.setCurrentId(this.currentId);
        MessageFactory.setCurrentX(currentX);
        MessageFactory.setCurrentY(currentY);

        this.myReceiverHandler = new MyReceiverHandler(itemService, prepaymentService, this);
        new Thread(this.myReceiverHandler).start();
    }

    public void sendStockRequestMessage(String itemCode, int quantity) {
        myReceiverHandler.changeWaitingMessageType(STOCK_RESPONSE);
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
        myReceiverHandler.changeWaitingMessageType(SALE_RESPONSE);
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
        myReceiverHandler.changeWaitingMessageType(NONE);
        Vector<Message> messages = myReceiverHandler.getResponseMessages();
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
        myReceiverHandler.changeWaitingMessageType(messageType);
    }

    private void clearResponseMessages() {
        myReceiverHandler.clearResponseMessages();
    }
}