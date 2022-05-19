package dvm.network.message;

import dvm.network.MessageParser;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Vector;

import static dvm.network.message.MessageType.STOCK_RESPONSE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StockResponseMessageTest {

    @Test
    void StockResponseMessage() {
        String dstId = "Team2";
        String itemCode = "10";
        int quantity = 15;

        StockResponseMessage sendingMessage = new StockResponseMessage(dstId, itemCode, quantity);
        StockResponseMessage checkMessage = new StockResponseMessage(Message.getCurrentId(), dstId, STOCK_RESPONSE.toString(), itemCode, quantity,
                Message.getCurrentId(), Message.getCurrentX(), Message.getCurrentY());

        assertThat(sendingMessage.getMessageType()).isEqualTo(STOCK_RESPONSE);
        assertThat(sendingMessage).usingRecursiveComparison().isEqualTo(checkMessage);
    }

    @Test
    void testToString() {
        String dstId = "Team2";
        String itemCode = "10";
        int quantity = 15;

        Message.setCurrentId("Team3");
        Message.setCurrentX(20);
        Message.setCurrentY(35);

        StockResponseMessage sendingMessage = new StockResponseMessage(dstId, itemCode, quantity);
        StockResponseMessage checkMessage = new StockResponseMessage(Message.getCurrentId(), dstId, STOCK_RESPONSE.toString(), itemCode, quantity,
                Message.getCurrentId(), Message.getCurrentX(), Message.getCurrentY());

        assertThat(sendingMessage.toString()).isEqualTo("Team3_Team2_1_10_15_Team3_20_35");
        assertThat(checkMessage.toString()).isEqualTo("Team3_Team2_1_10_15_Team3_20_35");
    }

    @Test
    void compareTo() {
        Message.setCurrentId("Team6");
        Message.setCurrentX(10);
        Message.setCurrentY(15);

        Vector<StockResponseMessage> messages = new Vector<>();
        messages.add((StockResponseMessage) MessageParser.createMessage("Team5_Team6_1_10_15_Team5_30_15"));
        messages.add((StockResponseMessage) MessageParser.createMessage("Team2_Team6_1_10_15_Team2_20_15"));
        messages.add((StockResponseMessage) MessageParser.createMessage("Team1_Team6_1_10_15_Team1_99_99"));
        messages.add((StockResponseMessage) MessageParser.createMessage("Team4_Team6_1_10_15_Team4_12_15"));
        messages.add((StockResponseMessage) MessageParser.createMessage("Team3_Team6_1_10_15_Team3_12_15"));
        Collections.sort(messages);

        assertThat(messages.get(0).getSrcId()).isEqualTo("Team3");
        assertThat(messages.get(1).getSrcId()).isEqualTo("Team4");
        assertThat(messages.get(2).getSrcId()).isEqualTo("Team2");
        assertThat(messages.get(3).getSrcId()).isEqualTo("Team5");
        assertThat(messages.get(4).getSrcId()).isEqualTo("Team1");
    }
}