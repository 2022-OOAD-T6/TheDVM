package dvm.network;

import dvm.domain.PrepaymentInfo;
import dvm.network.message.*;
import dvm.service.ItemService;
import dvm.service.PrepaymentService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Vector;

import static dvm.network.message.MessageType.*;

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
                System.out.println("readLine = " + request);
                Message message = MessageParser.createMessage(request);
                if (waitingMessageType == message.getMessageType()) {
                    responseMessages.add(message);
                    System.out.println("받은 메세지: " + message + " | " + waitingMessageType.getTypeName() + " 메세지 추가");
                } else {
                    handleReceivedMessage(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleReceivedMessage(Message message) {
        switch (message.getMessageType()) {
            case STOCK_REQUEST:
                System.out.println("받은 메세지: " + message + " | " + STOCK_REQUEST.getTypeName() + " 처리");
                responseStockRequest((StockRequestMessage) message);
                break;
            case STOCK_RESPONSE:
                System.out.println("받은 메세지: " + message + " | 현재는 " + STOCK_RESPONSE.getTypeName() + " 메세지는 무시합니다. " + waitingMessageType.getTypeName() + " 을 기다리는 중입니다.");
                break;
            case PREPAYMENT_INFO:
                System.out.println("받은 메세지: " + message + " | " + PREPAYMENT_INFO.getTypeName() + " 처리");
                PrepaymentInfoMessage prepaymentMessage = (PrepaymentInfoMessage) message;
                prepaymentService.savePrepaymentInfo(prepaymentMessage.getVerificationCode(), prepaymentMessage.getItemCode(), prepaymentMessage.getQuantity());
                break;
            case SALE_REQUEST:
                System.out.println("받은 메세지: " + message + " | " + SALE_REQUEST.getTypeName() + " 처리");
                responseSaleRequest((SaleRequestMessage) message);
                break;
            case SALE_RESPONSE:
                System.out.println("받은 메세지: " + message + " | 현재는 " + SALE_RESPONSE.getTypeName() + " 메세지는 무시합니다. " + waitingMessageType.getTypeName() + " 을 기다리는 중입니다.");
                break;
            case NONE:
                System.out.println("받은 메세지: " + message + " | 해당하는 타입이 없습니다. " + waitingMessageType.getTypeName() + " 을 기다리는 중입니다.");
        }
    }

    private void responseStockRequest(StockRequestMessage message) {
        int count = itemService.getItemCount(message.getItemCode());
        networkService.sendStockResponseMessage(message.getSrcId(), message.getItemCode(), count);
    }

    private void responseSaleRequest(SaleRequestMessage message) {
        if (itemService.isEnough(message.getItemCode(), message.getQuantity())) {
            networkService.sendSaleResponseMessage(message.getSrcId(), message.getItemCode());
        }
    }

}