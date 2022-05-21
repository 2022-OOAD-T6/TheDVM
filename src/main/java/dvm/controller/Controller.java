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
import java.util.logging.Logger;

import static dvm.domain.ResponseType.*;

public class Controller {
    private final Logger logger = Logger.getGlobal();
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
        return new Response<>(true, ITEMS_OK, items);
    }

    public Response<String> enterCardNum(String cardNum) {
        boolean result = cardService.saveCardNum(cardNum);
        if (result) {
            return new Response<>(true, CARD_OK);
        }
        return new Response<>(false, NOT_EXIST_CARD);
    }

    public Response<PrepaymentInfo> enterVerificationCode(String verificationCode) {
        PrepaymentInfo info = prepaymentService.getPrepaymentInfo(verificationCode);
        if (info == null) {
            return new Response<>(false, NOT_EXIST_CODE);
        } else if (!info.isValid()) {
            return new Response<>(false, INVALID_PREPAYMENT);
        }
        return new Response<>(true, CODE_OK, info);
    }


    public Response<Message> selectItem(String itemCode, int quantity) {
        boolean result = itemService.isEnough(itemCode, quantity);
        if (result) {
            return new Response<>(true, SELECTION_OK);
        }

        networkService.sendSaleRequestMessage(itemCode, quantity);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Message message = networkService.getSaleResponseMessage();
        if (message == null) {
            return new Response<>(false, NO_RESPONSE_MESSAGE);
        }

        return new Response<>(false, RESPONSE_OK, message);
    }

    public Response<String> requestPayment(String itemCode, int quantity) {
        if (itemService.isEnough(itemCode, quantity)) {
            int price = itemService.getItemPrice(itemCode);
            if (cardService.pay(price * quantity)) {
                itemService.updateStock(itemCode, -quantity);
                return new Response<>(true, PAYMENT_OK);
            } else {
                return new Response<>(false, PAYMENT_FAIL);
            }
        } else {
            return new Response<>(false, NOT_ENOUGH_STOCK);
        }
    }

    public Response<String> requestPrepayment(String dstDvmId, String itemCode, int quantity) {
        networkService.sendStockRequestMessage(itemCode, quantity);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Message message = networkService.getStockResponseMessageFrom(dstDvmId);
        if (message == null) {
            return new Response<>(false, NO_RESPONSE_MESSAGE);
        } else if (message.getMsgDescription().getItemNum() < quantity) {
            return new Response<>(false, NOT_ENOUGH_STOCK);
        }

        int price = itemService.getItemPrice(itemCode);
        if (cardService.pay(price * quantity)) {
            String code = prepaymentService.generateVerificationCode();
            networkService.sendPrepaymentInfoMessage(dstDvmId, itemCode, quantity, code);

            return new Response<>(true, PREPAYMENT_OK, code);
        } else {
            return new Response<>(false, PREPAYMENT_FAIL);
        }

    }


    public Response<String> updateStock(String itemCode, int quantity) {
        if (this.itemService.updateStock(itemCode, quantity)) {
            return new Response<>(true, UPDATE_OK);
        } else {
            return new Response<>(false, UPDATE_FAIL);
        }

    }

}