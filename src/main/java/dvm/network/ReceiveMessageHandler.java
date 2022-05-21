package dvm.network;

import GsonConverter.Deserializer;
import GsonConverter.Serializer;
import Model.Message;
import dvm.service.ItemService;
import dvm.service.PrepaymentService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Logger;

import static dvm.network.MessageType.*;

/**
 *
 */
public class ReceiveMessageHandler implements Runnable {
    private final Socket socket;

    private final MessageType waitingMessageType;

    private final Vector<Message> responseMessages;

    private final ItemService itemService;

    private final PrepaymentService prepaymentService;

    private final NetworkService networkService;

    private static final Deserializer deserializer = new Deserializer();
    private static final Serializer serializer = new Serializer(); // for log
    private final static Logger logger = Logger.getGlobal();

    public ReceiveMessageHandler(Socket socket, MessageType waitingMessageType, Vector<Message> responseMessages, ItemService itemService, PrepaymentService prepaymentService, NetworkService networkService) {
        this.socket = socket;
        this.waitingMessageType = waitingMessageType;
        this.responseMessages = responseMessages;
        this.itemService = itemService;
        this.prepaymentService = prepaymentService;
        this.networkService = networkService;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String request;
            while ((request = in.readLine()) != null) {
                Message message = deserializer.json2Message(request);
                logger.info("from "+ message.getSrcId() + " | "+message.getMsgType() + " | " + request);
                if (waitingMessageType == MessageType.json2MessageType(message.getMsgType())) {
                    responseMessages.add(message);
                    logger.info(waitingMessageType + "type 메세지 추가 from "+message.getSrcId());
                } else {
                    handleReceivedMessage(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleReceivedMessage(Message message) {
        switch (MessageType.json2MessageType(message.getMsgType())) {
            case STOCK_REQUEST:
                logger.info(message.getSrcId() + "로부터 받은 " + STOCK_REQUEST.getTypeName() + " 처리");
                responseStockRequest(message.getSrcId(), message.getMsgDescription().getItemCode());
                break;
            case STOCK_RESPONSE:
                logger.warning(message.getSrcId() + "로부터 받은 " + STOCK_RESPONSE.getTypeName() + " 무시함. " + waitingMessageType.getTypeName() + "을 기다리는 중입니다.");
                break;
            case PREPAYMENT_CHECK:
                logger.info(message.getSrcId() + "로부터 받은 " + PREPAYMENT_CHECK.getTypeName() + " 처리");
                prepaymentService.savePrepaymentInfo(itemService, message.getMsgDescription().getAuthCode(),  message.getMsgDescription().getItemCode(),  message.getMsgDescription().getItemNum());
                break;
            case SALE_REQUEST:
                logger.info(message.getSrcId() + "로부터 받은 " + SALE_REQUEST.getTypeName() + " 처리");
                responseSaleRequest(message.getSrcId(), message.getMsgDescription().getItemCode(), message.getMsgDescription().getItemNum());
                break;
            case SALE_RESPONSE:
                logger.warning(message.getSrcId() + "로부터 받은 " + SALE_RESPONSE.getTypeName() + " 무시함. " + waitingMessageType.getTypeName() + "을 기다리는 중입니다.");
                break;
            case NONE:
                logger.severe(message.getSrcId() + "로부터 받은 메세지에 해당하는 타입이 없음");
        }
    }

    private void responseStockRequest(String dstId, String itemCode) {
        int count = 10;
        //int count = itemService.getItemCount(itemCode);
        networkService.sendStockResponseMessage(dstId, itemCode, count);
    }

    private void responseSaleRequest(String dstId, String itemCode, int itemNum) {
        if (itemService.isEnough(itemCode, itemNum)) {
            networkService.sendSaleResponseMessage(dstId, itemCode);
        }
    }
}