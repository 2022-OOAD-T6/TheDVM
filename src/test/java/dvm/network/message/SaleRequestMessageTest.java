package dvm.network.message;

import org.junit.jupiter.api.Test;

import static dvm.network.message.MessageType.SALE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SaleRequestMessageTest {

    @Test
    void SaleRequestMessage(){
        String itemCode = "01";
        int quantity = 5;

        SaleRequestMessage sendingMessage = new SaleRequestMessage(itemCode, quantity);
        SaleRequestMessage checkMessage = new SaleRequestMessage(Message.getCurrentId(), "0", SALE_REQUEST.toString(), itemCode, quantity);

        assertThat(sendingMessage.getMessageType()).isEqualTo(SALE_REQUEST);
        assertThat(sendingMessage).usingRecursiveComparison().isEqualTo(checkMessage);

    }

    @Test
    void testToString() {
        String itemCode = "01";
        int quantity = 5;

        Message.setCurrentId("Team6");

        SaleRequestMessage sendingMessage = new SaleRequestMessage(itemCode, quantity);
        SaleRequestMessage checkMessage = new SaleRequestMessage(Message.getCurrentId(), "0", SALE_REQUEST.toString(), itemCode, quantity);

        assertThat(sendingMessage.toString()).isEqualTo("Team6_0_3_01_5");
        assertThat(checkMessage.toString()).isEqualTo("Team6_0_3_01_5");
    }
}