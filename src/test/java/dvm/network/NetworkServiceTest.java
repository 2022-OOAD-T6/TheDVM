package dvm.network;

import GsonConverter.Serializer;
import Model.Message;
import dvm.repository.ItemRepository;
import dvm.repository.PrepaymentRepository;
import dvm.service.ItemService;
import dvm.service.PrepaymentService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NetworkServiceTest {

    private final NetworkService networkService = new NetworkService("Team6", 10, 10,
            new ItemService(new ItemRepository()), new PrepaymentService(new PrepaymentRepository()));

    private final Serializer serializer = new Serializer();

    @Test
    void getResponseMessage() throws InterruptedException {
        networkService.changeWaitingMessageType(MessageType.SALE_RESPONSE);
        networkService.sendSaleResponseMessage("Team6", "05");
        Thread.sleep(1000);
        Message message = networkService.getSaleResponseMessage();
        assertThat(serializer.message2Json(message)).isEqualTo("{\"srcId\":\"Team6\",\"dstID\":\"Team6\",\"msgType\":\"SalesCheckResponse\",\"msgDescription\":{\"itemCode\":\"05\",\"itemNum\":0,\"dvmXCoord\":10,\"dvmYCoord\":10}}");

        networkService.changeWaitingMessageType(MessageType.STOCK_RESPONSE);
        MessageFactory.setCurrentId("Team4");
        networkService.sendStockResponseMessage("Team6", "05", 15);
        MessageFactory.setCurrentId("Team3");
        networkService.sendStockResponseMessage("Team6", "05", 20);
        Thread.sleep(1000);
        message = networkService.getStockResponseMessageFrom("Team3");
        assertThat(serializer.message2Json(message)).isEqualTo("{\"srcId\":\"Team3\",\"dstID\":\"Team6\",\"msgType\":\"StockCheckResponse\",\"msgDescription\":{\"itemCode\":\"05\",\"itemNum\":20,\"dvmXCoord\":10,\"dvmYCoord\":10}}");
    }

}