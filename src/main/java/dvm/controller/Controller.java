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

    public Response<String> enterCardNumber(String cardNumber) {
        boolean result = cardService.saveCardNumber(cardNumber);
        if (result) {
            return new Response<>(true, CARD_OK);
        }
        return new Response<>(false, NOT_EXIST_CARD);
    }

    public Response<PrepaymentInfo> enterVerificationCode(String verificationCode) {
        PrepaymentInfo information = prepaymentService.getPrepaymentInfo(verificationCode);
        if (information == null) {
            return new Response<>(false, NOT_EXIST_CODE);
        } else if (!information.isValid()) {
            return new Response<>(false, INVALID_PREPAYMENT);
        }
        return new Response<>(true, CODE_OK, information);
    }


    public Response<Message> selectItem(String itemCode, int quantity) {
        boolean result = itemService.isEnough(itemCode, quantity);
        if (result) {
            return new Response<>(true, SELECTION_OK);
        }
        networkService.sendSaleRequestMessage(itemCode, quantity);
        try {
            Thread.sleep(5000);
            Message message = networkService.getSaleResponseMessage(itemCode);
            if (message == null) {
                logger.warning("Controller | SaleResponseMessage 없음 | 받은 SaleResponseMessage가 없습니다.");
                return new Response<>(false, NO_RESPONSE_MESSAGE);
            }
            logger.info("Controller | SaleResponseMessage 받음 | from " + message.getSrcId() + " | 보유 수량: " + message.getMsgDescription().getItemNum());
            return new Response<>(true, RESPONSE_OK, message);
        } catch (Exception e) {
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

        Message message = networkService.getStockResponseMessageFrom(dstDvmId, itemCode, quantity);
        if (message == null) {
            logger.warning("Controller | StockResponseMessage 없음 | 받은 StockResponseMessage가 없습니다.");
            return new Response<>(false, NO_RESPONSE_MESSAGE);
        } else if (message.getMsgDescription().getItemNum() < quantity) {
            logger.info("Controller | StockResponseMessage 받음 | from " + message.getSrcId() + " | 하지만 재고가 부족합니다.");
            return new Response<>(false, NOT_ENOUGH_STOCK);
        }
        logger.info("Controller | StockResponseMessage 받음 | from " + message.getSrcId() + " | 재고가 충분합니다.");

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