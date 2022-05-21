package dvm.controller;

import Model.Message;
import dvm.domain.Item;
import dvm.domain.PrepaymentInfo;
import dvm.network.NetworkService;
import dvm.domain.Response;
import dvm.service.CardService;
import dvm.service.ItemService;
import dvm.service.PrepaymentService;

import java.util.List;
import java.util.Vector;

public class Controller {

    private final NetworkService networkService;
    private final ItemService itemService;
    private final PrepaymentService prepaymentService;
    private final CardService cardService;

    public Controller(NetworkService networkService, ItemService itemService, PrepaymentService prepaymentService, CardService cardService) {
        this.networkService = networkService;
        this.itemService = itemService;
        this.cardService = cardService;
        this.prepaymentService = prepaymentService;
    }

    public Response<List<Item>> getAllItems() {
        List<Item> items = itemService.getItems();
        return new Response<>(true, "", items);
    }

    public Response<String> enterCardNum(String cardNum) {
        boolean result = cardService.saveCardNum(cardNum);
        return new Response<>(result, "");
    }

    public Response<PrepaymentInfo> enterVerificationCode(String verificationCode) {
        PrepaymentInfo info = prepaymentService.getPrepaymentInfo(verificationCode);
        if (info == null) {
            return new Response<>(false, "");
        } else if (!info.isValid()) {
            return new Response<>(false, "");
        }
        return new Response<>(true, "", info);
    }


    public Response<Message> selectItem(String itemCode, int quantity) {
        boolean result = itemService.isEnough(itemCode, quantity);
        if (result) {
            return new Response<>(true, "결제 진행");
        }

        networkService.sendSaleRequestMessage(itemCode, quantity);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Vector<Message> messages = networkService.getSaleResponseMessages();

        if (messages.isEmpty()) {
            return new Response<>(false, "다른 자판기 응답이 없드라");
        }

        Message responseMessage = messages.get(0);
        networkService.clearResponseMessages();

        return new Response<>(false, "여기서 대신 결제?", responseMessage);

    }

    public Response<String> requestPayment(String itemCode, int quantity) {
        if (itemService.isEnough(itemCode, quantity)) {
            int price = itemService.getItemPrice(itemCode);

            if (cardService.pay(price)) {
                itemService.updateStock(itemCode, -quantity);
                return new Response<>(true, "");
            } else {
                return new Response<>(false, "");
            }
        } else {
            return new Response<>(false, "");
        }
    }

    public Response<String> requestPrepayment(String dstDvmId, String itemCode, int quantity) {
        networkService.sendStockRequestMessage(itemCode, quantity);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public Response<String> updateStock(String itemCode, int quantity) {
        if (this.itemService.updateStock(itemCode, quantity)) {
            return new Response<>(true, "");
        } else {
            return new Response<>(false, "");
        }

    }

}