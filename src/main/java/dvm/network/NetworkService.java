package dvm.network;

import dvm.network.message.*;

import java.util.List;
import java.util.Vector;

/**
 * 
 */
public class NetworkService {

    private Sender sender;

    private Receiver receiver;

    private List<NetworkInfo> dvmNetworkInfo;

    public NetworkService() {
        sender = new Sender();

        // 임시 네트워크 정보
        dvmNetworkInfo.add(new NetworkInfo("127.0.0.1", "1000"));
        dvmNetworkInfo.add(new NetworkInfo("127.0.0.1", "1000"));
        dvmNetworkInfo.add(new NetworkInfo("127.0.0.1", "1000"));
        dvmNetworkInfo.add(new NetworkInfo("127.0.0.1", "1000"));
        dvmNetworkInfo.add(new NetworkInfo("127.0.0.1", "1000"));
        dvmNetworkInfo.add(new NetworkInfo("127.0.0.1", "1000"));

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