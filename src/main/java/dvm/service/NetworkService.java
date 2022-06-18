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

    private final Receiver receiver;

    private final Sender sender;
    private final Logger logger = Logger.getGlobal();
    private final Serializer serializer = new Serializer();

    public NetworkService(ItemService itemService, PrepaymentService prepaymentService) {
        // 리시버 구현체는 여기서 선택
//        this.receiver = new NettyReceiver(itemService, prepaymentService, this);
        this.sender = new Sender();
//        this.receiver = new ServerSocketReceiver(itemService, prepaymentService, this);
        this.receiver = new EventNettyReceiver(itemService, prepaymentService, this);

        new Thread(this.receiver).start();
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

    public Message getSaleResponseMessage(String itemCode) throws IllegalStateException {
        Vector<Message> saleResponseMessages = getMessages(SALE_RESPONSE);
        clearResponseMessages();

        return saleResponseMessages.stream()
                .filter(message -> message.getMsgDescription().getItemCode().equals(itemCode))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("No Sale Response Message Received"));
    }

    public Message getStockResponseMessageFrom(String srcId, String itemCode, int quantity)
            throws IllegalStateException {
        Vector<Message> stockResponseMessages = getMessages(STOCK_RESPONSE);
        clearResponseMessages();

        return stockResponseMessages.stream()
                .filter(message -> message.getSrcId().equals(srcId) &&
                        message.getMsgDescription().getItemCode().equals(itemCode) &&
                        message.getMsgDescription().getItemNum() >= quantity)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("No Stock Response Message Received"));
    }

    private Vector<Message> getMessages(MessageType messageType) {
        receiver.changeWaitingMessageType(NONE);
        Vector<Message> messages = receiver.getResponseMessages();
        Vector<Message> responseMessage = new Vector<>();
        logger.info("----------저장된 메세지------------");
        for (Message message : messages) {
            System.out.println(serializer.message2Json(message));
        }
        logger.info("--------------------------------");

        messages.stream()
                .filter(message -> message.getMsgType().equals(messageType.getTypeName()))
                .forEach(responseMessage::add);
        MessageFactory.sortMessages(responseMessage);
        return responseMessage;
    }

    public void changeWaitingMessageType(MessageType messageType) {
        receiver.changeWaitingMessageType(messageType);
    }

    private void clearResponseMessages() {
        receiver.clearResponseMessages();
    }
}