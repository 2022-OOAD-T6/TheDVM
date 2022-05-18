package dvm.controller;

import dvm.network.NetworkService;
import dvm.network.Response;
import dvm.service.CardService;
import dvm.service.ItemService;
import dvm.service.PrepaymentService;

/**
 * 
 */
public class Controller {

    /**
     * Default constructor
     */
    public Controller() {
    }

    /**
     * 
     */
    private NetworkService networkService;

    /**
     * 
     */
    private ItemService itemService;

    /**
     * 
     */
    private PrepaymentService prepaymentService;

    /**
     * 
     */
    private CardService cardService;

    /**
     * @param cardNum 
     * @return
     */
    public Response enterCardNum(String cardNum) {
        // TODO implement here
        this.cardService.saveCardNum(cardNum);
        return null;
    }

    /**
     * @param verificationCode 
     * @return
     */
    public Response enterVerificationCode(String verificationCode) {
        // TODO implement here
        this.prepaymentService.getPrepaymentInfo(verificationCode);
        return null;
    }

    /**
     * @param itemCode 
     * @param quantity 
     * @return
     */
    public Response selectItem(String itemCode, int quantity) {
        // TODO implement here
        this.itemService.isEnough(itemCode,quantity);
        return null;
    }

    /**
     * @param itemCode 
     * @param quantity 
     * @return
     */
    public Response requestPayment(String itemCode, int quantity) {
        // TODO implement here
        this.itemService.isEnough(itemCode,quantity);

        int itemPrice = this.itemService.getItemPrice(itemCode);
        this.cardService.pay(itemPrice);
        return null;
    }

    /**
     * @param itemCode 
     * @param quantity 
     * @return
     */
    public Response updateStock(String itemCode, int quantity) {
        // TODO implement here
        return null;
    }

}