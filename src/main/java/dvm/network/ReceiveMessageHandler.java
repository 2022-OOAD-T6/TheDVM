package dvm.network;

import GsonConverter.Deserializer;
import Model.Message;
import dvm.service.ItemService;
import dvm.service.NetworkService;
import dvm.service.PrepaymentService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Logger;

import static dvm.network.MessageType.*;

/**
 * 수신 메시지 핸들링 클래스
 * 리시버를 ReceiverImpl 클래스로 사용하는 경우에만 쓰이는 클래스
 */
public class ReceiveMessageHandler implements Runnable {
    private final Socket socket;

    private final MessageType waitingMessageType;

    private final Vector<Message> responseMessages;

    private final ItemService itemService;

    private final PrepaymentService prepaymentService;

    private final NetworkService networkService;

    private static final Deserializer deserializer = new Deserializer();
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
                System.out.println("메세지 받음 | from " + message.getSrcId() + " | " + message.getMsgType() + " | " + request);
                if (waitingMessageType == MessageType.json2MessageType(message.getMsgType())) {
                    responseMessages.add(message);
                    logger.info(waitingMessageType + " type 메세지 추가 from " + message.getSrcId());
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
                System.out.println(message.getSrcId() + "로부터 받은 " + STOCK_REQUEST.getTypeName() + " 처리");
                responseStockRequest(message.getSrcId(), message.getMsgDescription().getItemCode(), message.getMsgDescription().getItemNum());
                break;
            case STOCK_RESPONSE:
                logger.warning(message.getSrcId() + "로부터 받은 " + STOCK_RESPONSE.getTypeName() + " 무시함. " + waitingMessageType.getTypeName() + "을 기다리는 중입니다.");
                break;
            case PREPAYMENT_CHECK:
                System.out.println(message.getSrcId() + "로부터 받은 " + PREPAYMENT_CHECK.getTypeName() + " 처리");
                prepaymentService.savePrepaymentInfo(itemService, message.getMsgDescription().getAuthCode(), message.getMsgDescription().getItemCode(), message.getMsgDescription().getItemNum());
                break;
            case SALE_REQUEST:
                System.out.println(message.getSrcId() + "로부터 받은 " + SALE_REQUEST.getTypeName() + " 처리");
                responseSaleRequest(message.getSrcId(), message.getMsgDescription().getItemCode(), message.getMsgDescription().getItemNum());
                break;
            case SALE_RESPONSE:
                logger.warning(message.getSrcId() + "로부터 받은 " + SALE_RESPONSE.getTypeName() + " 무시함. " + waitingMessageType.getTypeName() + "을 기다리는 중입니다.");
                break;
            case NONE:
                logger.severe(message.getSrcId() + "로부터 받은 메세지에 해당하는 타입이 없음");
        }
    }

    private void responseStockRequest(String dstId, String itemCode, int itemNum) {
        if (itemService.isEnough(itemCode, itemNum)) {
            networkService.sendStockResponseMessage(dstId, itemCode, itemNum);
        }
    }

    private void responseSaleRequest(String dstId, String itemCode, int itemNum) {
        if (itemService.isEnough(itemCode, itemNum)) {
            networkService.sendSaleResponseMessage(dstId, itemCode);
        }
    }
}