package dvm.controller;

import dvm.network.NetworkService;
import dvm.domain.Response;
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
    public Controller(NetworkService networkService, ItemService itemService, PrepaymentService prepaymentService, CardService cardService) {
        this.networkService = networkService;
        this.itemService = itemService;
        this.cardService = cardService;
        this.prepaymentService = prepaymentService;
    }
    public Controller(){

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
        if(this.itemService.isEnough(itemCode,quantity)==true){
            int itemPrice = this.itemService.getItemPrice(itemCode);
            if(this.cardService.pay(itemPrice)==true){
                this.itemService.updateStock(itemCode,quantity);
                return null;            //결제 및 업데이트 성공
            }else
                return null;            //잔액 부족
        }else
            return null;                //수량 부족
    }


    public Response updateStock(String itemCode, int quantity) {
        // TODO implement here
        if(this.itemService.updateStock(itemCode,quantity)==true){
            return null;                //업데이트 성공
        }else
            return null;                //업데이트 실패
    }

}