package dvm.controller;

import Model.Message;
import dvm.domain.Item;
import dvm.domain.PrepaymentInfo;
import dvm.service.NetworkService;
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

    public Response<List<Item>> getMyItems() {
        List<Item> items = itemService.getMyItems();
        return new Response<>(true, ITEMS_OK, items);
    }

    public Response<Integer> getItemCount(String itemCode) {
        return new Response<>(true, ITEMS_OK, itemService.getItemCount(itemCode));
    }

    public Response<String> enterCardNum(String cardNum) {
        boolean result = cardService.saveCardNum(cardNum);
        if (result) {
            return new Response<>(true, CARD_OK);
        }
        return new Response<>(false, NOT_EXIST_CARD);
    }

    public Response<PrepaymentInfo> enterVerificationCode(String verificationCode) {
        try {
            PrepaymentInfo info = prepaymentService.getPrepaymentInfo(verificationCode);
            if (info.isValid()) {
                return new Response<>(true, CODE_OK, info);
            } else {
                return new Response<>(false, INVALID_PREPAYMENT);
            }
        } catch (IllegalArgumentException e) {
            return new Response<>(false, NOT_EXIST_CODE);
        }
    }


    public Response<Message> selectItem(String itemCode, int quantity) {
        if (itemService.isEnough(itemCode, quantity)) {
            return new Response<>(true, SELECTION_OK);
        }

        networkService.sendSaleRequestMessage(itemCode, quantity);

        try {
            Thread.sleep(5000);
            Message message = networkService.getSaleResponseMessage(itemCode);
            logger.info("Controller | SaleResponseMessage ?????? | from " + message.getSrcId() + " | ?????? ??????: " + message.getMsgDescription().getItemNum());
            return new Response<>(true, RESPONSE_OK, message);
        } catch (IllegalStateException | InterruptedException e) {
            logger.warning("Controller | SaleResponseMessage ?????? | ?????? SaleResponseMessage??? ????????????.");
            return new Response<>(false, NO_RESPONSE_MESSAGE);
        }
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
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Message message = networkService.getStockResponseMessageFrom(dstDvmId, itemCode, quantity);
            if (message.getMsgDescription().getItemNum() < quantity) {
                logger.info("Controller | StockResponseMessage ?????? | from " + message.getSrcId() + " | ????????? ????????? ???????????????.");
                return new Response<>(false, NOT_ENOUGH_STOCK);
            }

            logger.info("Controller | StockResponseMessage ?????? | from " + message.getSrcId() + " | ????????? ???????????????.");
            int price = itemService.getItemPrice(itemCode);

            if (cardService.pay(price * quantity)) {
                String code = prepaymentService.generateVerificationCode();
                networkService.sendPrepaymentInfoMessage(dstDvmId, itemCode, quantity, code);
                return new Response<>(true, PREPAYMENT_OK, code);
            } else {
                return new Response<>(false, PREPAYMENT_FAIL);
            }
        } catch (IllegalStateException e) {
            logger.warning("Controller | StockResponseMessage ?????? | ?????? StockResponseMessage??? ????????????.");
            return new Response<>(false, NO_RESPONSE_MESSAGE);
        } catch (IllegalArgumentException e) {
            logger.warning("Controller | ???????????? ?????? ItemCode??? ???????????????.");
            return new Response<>(false, NOT_EXIST_CODE);
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