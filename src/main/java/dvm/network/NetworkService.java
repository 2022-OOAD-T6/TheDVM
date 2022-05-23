package dvm.network;

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

    private final Sender sender;

    private final Receiver receiver;
    private final Logger logger = Logger.getGlobal();

    public NetworkService(String currentId, int currentX, int currentY, ItemService itemService, PrepaymentService prepaymentService) {
        this.sender = new Sender();
        this.receiver = new Receiver(itemService, prepaymentService, this);

        MessageFactory.setCurrentId(currentId);
        MessageFactory.setCurrentX(currentX);
        MessageFactory.setCurrentY(currentY);

        Thread serverThread = new Thread(receiver);
        serverThread.start();
        logger.info("Run thread");
    }

    public void sendStockRequestMessage(String itemCode, int quantity) {
        receiver.changeWaitingMessageType(STOCK_RESPONSE);
        sender.send(MessageFactory.createStockRequestMessage(itemCode, quantity));
    }

    public void sendStockResponseMessage(String dstId, String itemCode, int quantity) {
        sender.send(MessageFactory.createStockResponseMessage(dstId, itemCode, quantity));
    }

    public void sendPrepaymentInfoMessage(String dstId, String itemCode, int quantity, String verificationCode) {
        sender.send(MessageFactory.createPrepaymentCheckMessage(dstId, itemCode, quantity, verificationCode));
    }

    public void sendSaleRequestMessage(String itemCode, int quantity) {
        receiver.changeWaitingMessageType(SALE_RESPONSE);
        sender.send(MessageFactory.createSaleRequestMessage(itemCode, quantity));
    }

    public void sendSaleResponseMessage(String dstId, String itemCode) {
        sender.send(MessageFactory.createSaleResponseMessage(dstId, itemCode));
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