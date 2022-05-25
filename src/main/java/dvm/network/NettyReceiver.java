package dvm.network;

import DVM_Server.DVMServer;
import Model.Message;
import dvm.service.ItemService;
import dvm.service.NetworkService;
import dvm.service.PrepaymentService;

import java.util.Vector;
import java.util.logging.Logger;

import static dvm.network.MessageType.*;

/**
 * Receiver 구현 클래스
 * DvmServer 라이브러리 사용하는 버전
 */
public class NettyReceiver implements Receiver {

    private int consumed = 0;
    private MessageType waitingMessageType;

    private final Vector<Message> responseMessages;

    private final ItemService itemService;

    private final PrepaymentService prepaymentService;

    private final NetworkService networkService;
    private final static Logger logger = Logger.getGlobal();


    public NettyReceiver(ItemService itemService, PrepaymentService prepaymentService,
                         NetworkService networkService) {

        this.waitingMessageType = MessageType.NONE;
        this.responseMessages = new Vector<>();
        this.itemService = itemService;
        this.prepaymentService = prepaymentService;
        this.networkService = networkService;

        DvmServerCreator dvmServer = new DvmServerCreator();
        new Thread(dvmServer).start();
    }

    @Override
    public void run() {
        while (true) {
            if (DVMServer.msgList.size() > consumed) {
                Message message = DVMServer.msgList.get(consumed);

                if (waitingMessageType == MessageType.json2MessageType(message.getMsgType())) {
                    responseMessages.add(message);
                    logger.info(waitingMessageType + " type 메세지 추가 from " + message.getSrcId());
                } else {
                    handleReceivedMessage(message);
                }

                consumed++;
            } else {
                Thread.yield();
            }
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
        return responseMessages;
    }

    @Override
    public void clearResponseMessages() {
        responseMessages.clear();
    }
}
