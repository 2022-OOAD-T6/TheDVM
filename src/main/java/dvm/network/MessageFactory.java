package dvm.network;

import Model.Message;

import java.util.Comparator;
import java.util.Vector;

import static dvm.network.MessageType.*;

public class MessageFactory {

    private static String currentId = "Team6";
    private static int currentX = 5;
    private static int currentY = 5;

    private MessageFactory() {

    }

    public static void setCurrentId(String currentId) {
        MessageFactory.currentId = currentId;
    }

    public static void setCurrentX(int currentX) {
        MessageFactory.currentX = currentX;
    }

    public static void setCurrentY(int currentY) {
        MessageFactory.currentY = currentY;
    }

    public static Message createStockRequestMessage(String itemCode, int quantity) {
        Message message = new Message();
        message.setSrcId(currentId);
        message.setDstID("0");
        message.setMsgType(STOCK_REQUEST.getTypeName());
        Message.MessageDescription messageDescription = new Message.MessageDescription();
        messageDescription.setItemCode(itemCode);
        messageDescription.setItemNum(quantity);
        message.setMsgDescription(messageDescription);
        return message;
    }

    public static Message createStockResponseMessage(String dstId, String itemCode, int quantity) {
        Message message = new Message();
        message.setSrcId(currentId);
        message.setDstID(dstId);
        message.setMsgType(STOCK_RESPONSE.getTypeName());
        Message.MessageDescription messageDescription = new Message.MessageDescription();
        messageDescription.setItemCode(itemCode);
        messageDescription.setItemNum(quantity);
        messageDescription.setDvmXCoord(currentX);
        messageDescription.setDvmYCoord(currentY);
        message.setMsgDescription(messageDescription);
        return message;
    }

    public static Message createPrepaymentCheckMessage(String dstId, String itemCode, int quantity, String verificationCode) {
        Message message = new Message();
        message.setSrcId(currentId);
        message.setDstID(dstId);
        message.setMsgType(PREPAYMENT_CHECK.getTypeName());
        Message.MessageDescription messageDescription = new Message.MessageDescription();
        messageDescription.setItemCode(itemCode);
        messageDescription.setItemNum(quantity);
        messageDescription.setAuthCode(verificationCode);
        message.setMsgDescription(messageDescription);
        return message;
    }

    public static Message createSaleRequestMessage(String itemCode, int quantity) {
        Message message = new Message();
        message.setSrcId(currentId);
        message.setDstID("0");
        message.setMsgType(SALE_REQUEST.getTypeName());
        Message.MessageDescription messageDescription = new Message.MessageDescription();
        messageDescription.setItemCode(itemCode);
        messageDescription.setItemNum(quantity);
        message.setMsgDescription(messageDescription);
        return message;
    }

    public static Message createSaleResponseMessage(String dstId, String itemCode) {
        Message message = new Message();
        message.setSrcId(currentId);
        message.setDstID(dstId);
        message.setMsgType(SALE_RESPONSE.getTypeName());
        Message.MessageDescription messageDescription = new Message.MessageDescription();
        messageDescription.setItemCode(itemCode);
        messageDescription.setDvmXCoord(currentX);
        messageDescription.setDvmYCoord(currentY);
        message.setMsgDescription(messageDescription);
        return message;
    }

    // 좌표 대로 정렬
    public static void sortMessages(Vector<Message> messages) {
        messages.sort(new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                int leftDifferX = currentX - o1.getMsgDescription().getDvmXCoord();
                int leftDifferY = currentY - o1.getMsgDescription().getDvmYCoord();
                double thisDistance = Math.sqrt(leftDifferX * leftDifferX + leftDifferY * leftDifferY);

                int rightDifferX = currentX - o2.getMsgDescription().getDvmXCoord();
                int rightDifferY = currentY - o2.getMsgDescription().getDvmYCoord();
                double otherDistance = Math.sqrt(rightDifferX * rightDifferX + rightDifferY * rightDifferY);

                if (Double.compare(thisDistance, otherDistance) == 0) {
                    if ((o1.getSrcId().compareTo(o2.getSrcId())) > 0) {
                        return 1;
                    }
                } else if (Double.compare(thisDistance, otherDistance) > 1) { // thisDistance가 더 크면 1
                    return 1;
                }
                return -1;
            }
        });
    }

    public static void printCurrentInfo() {
        System.out.println("현재 id: " + currentId);
        System.out.println("현재 x: " + currentX);
        System.out.println("현재 y: " + currentY);
    }
}
