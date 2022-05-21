package dvm.controller;

import dvm.network.NetworkService;
import dvm.network.Response;
import dvm.service.CardService;
import dvm.service.ItemService;
import dvm.service.PrepaymentService;

public class Controller {


    public Controller(NetworkService networkService, ItemService itemService, PrepaymentService prepaymentService, CardService cardService) {
        this.networkService = networkService;
        this.itemService = itemService;
        this.cardService = cardService;
        this.prepaymentService = prepaymentService;
    }
    public Controller(){

    }


    private NetworkService networkService;


    private ItemService itemService;


    private PrepaymentService prepaymentService;

    private CardService cardService;


    public Response<Boolean> enterCardNum(String cardNum) {
        // TODO implement here
        if(this.cardService.saveCardNum(cardNum)) {
            Response responseTrue = new Response(true, "", null);
            return responseTrue;
        }else {
            Response responseFalse = new Response(false, "", null);
            return responseFalse;
        }
    }

    public Response enterVerificationCode(String verificationCode) {
        // TODO implement here
        this.prepaymentService.getPrepaymentInfo(verificationCode);
        return null;
    }


    public Response selectItem(String itemCode, int quantity) {
        // TODO implement here
        this.itemService.isEnough(itemCode,quantity);
        return null;
    }

    public Response requestPayment(String itemCode, int quantity) {
        // TODO implement here
        if(this.itemService.isEnough(itemCode,quantity)){
            int itemPrice = this.itemService.getItemPrice(itemCode);
            if(this.cardService.pay(itemPrice)){
                this.itemService.updateStock(itemCode,quantity);
                return null;            //결제 및 업데이트 성공
            }else
                return null;            //잔액 부족
        }else
            return null;                //수량 부족
    }


    public Response updateStock(String itemCode, int quantity) {
        // TODO implement here
        if(this.itemService.updateStock(itemCode,quantity)){
            return null;                //업데이트 성공
        }else
            return null;                //업데이트 실패
    }

}