package dvm.service;

import GsonConverter.Serializer;
import Model.Message;
import dvm.network.MessageFactory;
import dvm.network.MessageType;
import dvm.repository.ItemRepository;
import dvm.repository.PrepaymentRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NetworkServiceTest {

    private final NetworkService networkService = new NetworkService(new ItemService(ItemRepository.getInstance()), new PrepaymentService(PrepaymentRepository.getInstance()));

    private final Serializer serializer = new Serializer();

    @Test
    void getResponseMessage() throws InterruptedException {
    //     String itemCode="05";
    //     networkService.changeWaitingMessageType(MessageType.SALE_RESPONSE);
    //     networkService.sendSaleResponseMessage("Team6", itemCode);
    //     Thread.sleep(1000);
    //     Message message = networkService.getSaleResponseMessage(itemCode);
    //     assertThat(serializer.message2Json(message)).isEqualTo("{\"srcId\":\"Team6\",\"dstID\":\"Team6\",\"msgType\":\"SalesCheckResponse\",\"msgDescription\":{\"itemCode\":\"05\",\"itemNum\":0,\"dvmXCoord\":10,\"dvmYCoord\":10}}");
    //
    //     networkService.changeWaitingMessageType(MessageType.STOCK_RESPONSE);
    //     MessageFactory.setCurrentId("Team4");
    //     networkService.sendStockResponseMessage("Team6", itemCode, 15);
    //     MessageFactory.setCurrentId("Team3");
    //     networkService.sendStockResponseMessage("Team6", itemCode, 20);
    //     Thread.sleep(1000);
    //     message = networkService.getStockResponseMessageFrom("Team3", itemCode, 20);
    //     assertThat(serializer.message2Json(message)).isEqualTo("{\"srcId\":\"Team3\",\"dstID\":\"Team6\",\"msgType\":\"StockCheckResponse\",\"msgDescription\":{\"itemCode\":\"05\",\"itemNum\":20,\"dvmXCoord\":10,\"dvmYCoord\":10}}");
    }

}