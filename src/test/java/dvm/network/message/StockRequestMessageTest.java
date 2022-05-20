package dvm.network.message;

import org.junit.jupiter.api.Test;

import static dvm.network.message.MessageType.STOCK_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StockRequestMessageTest {

    @Test
    void StockRequestMessage(){
        String itemCode = "15";
        int quantity = 8;

        StockRequestMessage sendingMessage = new StockRequestMessage(itemCode, quantity);
        StockRequestMessage checkMessage = new StockRequestMessage(Message.getCurrentId(),"0", STOCK_REQUEST.toString(), itemCode, quantity);

        assertThat(sendingMessage.getMessageType()).isEqualTo(STOCK_REQUEST);
        assertThat(sendingMessage).usingRecursiveComparison().isEqualTo(checkMessage);
    }

    @Test
    void testToString() {
        String itemCode = "15";
        int quantity = 8;

        Message.setCurrentId("Team6");

        StockRequestMessage sendingMessage = new StockRequestMessage(itemCode, quantity);
        StockRequestMessage checkMessage = new StockRequestMessage(Message.getCurrentId(),"0", STOCK_REQUEST.toString(), itemCode, quantity);

        assertThat(sendingMessage.toString()).isEqualTo("Team6_0_0_15_8");
        assertThat(checkMessage.toString()).isEqualTo("Team6_0_0_15_8");

    }
}