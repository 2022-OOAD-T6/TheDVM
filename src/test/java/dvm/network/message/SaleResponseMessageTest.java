package dvm.network.message;

import dvm.network.MessageParser;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Vector;

import static dvm.network.message.MessageType.SALE_RESPONSE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SaleResponseMessageTest {

    @Test
    void SaleResponseMessage() {
        String dstId = "Team1";
        String itemCode = "02";

        SaleResponseMessage sendingMessage = new SaleResponseMessage(dstId, itemCode);
        SaleResponseMessage checkMessage = new SaleResponseMessage(Message.getCurrentId(), dstId, SALE_RESPONSE.toString(), itemCode,
                Message.getCurrentId(), Message.getCurrentX(), Message.getCurrentY());

        assertThat(sendingMessage.getMessageType()).isEqualTo(SALE_RESPONSE);
        assertThat(sendingMessage).usingRecursiveComparison().isEqualTo(checkMessage);
    }

    @Test
    void testToString() {
        String dstId = "Team5";
        String itemCode = "10";

        Message.setCurrentId("Team6");
        Message.setCurrentX(10);
        Message.setCurrentY(15);

        SaleResponseMessage sendingMessage = new SaleResponseMessage(dstId, itemCode);
        SaleResponseMessage checkMessage = new SaleResponseMessage(Message.getCurrentId(), dstId, SALE_RESPONSE.toString(), itemCode,
                Message.getCurrentId(), Message.getCurrentX(), Message.getCurrentY());
        assertThat(sendingMessage.toString()).isEqualTo("Team6_Team5_4_10_Team6_10_15");
        assertThat(checkMessage.toString()).isEqualTo("Team6_Team5_4_10_Team6_10_15");
    }

    @Test
    void compareTo(){
        Message.setCurrentId("Team6");
        Message.setCurrentX(10);
        Message.setCurrentY(15);

        Vector<SaleResponseMessage> messages = new Vector<>();
        messages.add((SaleResponseMessage)MessageParser.createMessage("Team1_Team6_4_10_Team1_99_99"));
        messages.add((SaleResponseMessage)MessageParser.createMessage("Team2_Team6_4_10_Team2_20_15"));
        messages.add((SaleResponseMessage)MessageParser.createMessage("Team4_Team6_4_10_Team4_12_15"));
        messages.add((SaleResponseMessage)MessageParser.createMessage("Team5_Team6_4_10_Team5_30_15"));
        messages.add((SaleResponseMessage)MessageParser.createMessage("Team3_Team6_4_10_Team3_12_15"));
        Collections.sort(messages);

        assertThat(messages.get(0).getSrcId()).isEqualTo("Team3");
        assertThat(messages.get(1).getSrcId()).isEqualTo("Team4");
        assertThat(messages.get(2).getSrcId()).isEqualTo("Team2");
        assertThat(messages.get(3).getSrcId()).isEqualTo("Team5");
        assertThat(messages.get(4).getSrcId()).isEqualTo("Team1");
    }
}