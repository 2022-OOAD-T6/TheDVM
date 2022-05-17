package dvm.network;

import java.util.List;
import java.util.Vector;

/**
 * 
 */
public class NetworkService {

    /**
     * Default constructor
     */
    public NetworkService() {
    }

    /**
     * 
     */
    private Sender sender;

    /**
     * 
     */
    private Receiver receiver;

    /**
     * 
     */
    private List<NetworkInfo> dvmNetworkInfo;

    /**
     * @param itemCode 
     * @param quantity 
     * @return
     */
    public void sendSaleRequestMessage(String itemCode, int quantity) {
        // TODO implement here
        return null;
    }

    /**
     * @param itemCode 
     * @return
     */
    public void sendSaleResponseMessage(String itemCode) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Vector<Message> getSaleResponseMessages() {
        // TODO implement here
        return null;
    }

    /**
     * @param itemCode 
     * @return
     */
    public void sendStockResponseMessage(String itemCode) {
        // TODO implement here
        return null;
    }

    /**
     * @param itemCode 
     * @param quantity 
     * @return
     */
    public void sendStockRequestMessage(String itemCode, int quantity) {
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

    /**
     * @param itemCode 
     * @param quantity 
     * @param verificationCode 
     * @return
     */
    public void sendPrepaymentInfoMessage(String itemCode, int quantity, String verificationCode) {
        // TODO implement here
        return null;
    }

}