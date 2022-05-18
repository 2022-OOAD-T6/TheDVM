package dvm.network;

import dvm.network.message.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 
 */
public class NetworkService {

    private final Sender sender;

    private final Receiver receiver;

    private final List<NetworkInfo> dvmNetworkInfo;

    public NetworkService(Sender sender, Receiver receiver) {
        this.sender = sender;
        this.receiver = receiver;
        dvmNetworkInfo = new ArrayList<>();

        // 임시 네트워크 정보
        dvmNetworkInfo.add(new NetworkInfo("127.0.0.1", "1000"));
        dvmNetworkInfo.add(new NetworkInfo("127.0.0.1", "1001"));
        dvmNetworkInfo.add(new NetworkInfo("127.0.0.1", "1002"));
        dvmNetworkInfo.add(new NetworkInfo("127.0.0.1", "1003"));
        dvmNetworkInfo.add(new NetworkInfo("127.0.0.1", "1004"));

        // 현 DVM X,Y
        Message.setCurrentX(5);
        Message.setCurrentY(5);
    }

    public void sendStockRequestMessage(String itemCode, int quantity) {
        StockRequestMessage sendingMessage = new StockRequestMessage(itemCode, quantity);
        sender.send(sendingMessage);
    }

    public void sendStockResponseMessage(String itemCode, int quantity) {
        StockResponseMessage sendingMessage = new StockResponseMessage("어디로?", itemCode, quantity);
        sender.send(sendingMessage);
    }

    public void sendPrepaymentInfoMessage(String dstId, String itemCode, int quantity, String verificationCode) {
        PrepaymentInfoMessage sendingMessage = new PrepaymentInfoMessage(dstId, itemCode, quantity, verificationCode);
        sender.send(sendingMessage);
    }

    public void sendSaleRequestMessage(String itemCode, int quantity) {
        SaleRequestMessage sendingMessage = new SaleRequestMessage(itemCode, quantity);
        sender.send(sendingMessage);
    }

    public void sendSaleResponseMessage(String dstId, String itemCode) {
        SaleResponseMessage sendingMessage = new SaleResponseMessage(dstId, itemCode);
        sender.send(sendingMessage);
    }

    public Vector<Message> getSaleResponseMessages() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Vector<Message> getStockResponseMessages() {
        // TODO implement here
        return null;
    }
}