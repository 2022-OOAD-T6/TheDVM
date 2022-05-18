package dvm.network;

import dvm.network.message.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 서빙한 메세지 Parser Test
 */
class MessageParserTest {

    @Test
    public void invalidMessage() {
        try {
            Message invalidMessage = MessageParser.createMessage("d;fajswefjnal");
        } catch (Exception exception) {
            assertThat(exception.getMessage()).isEqualTo("메세지 생성 불가능");
        }
    }

    @Test
    public void createStockRequestMessage() throws Exception {
        String receivedMessage = "Team5_Team6_0_01_10";
        StockRequestMessage parsedMessage = (StockRequestMessage) MessageParser.createMessage(receivedMessage);
        assertThat(receivedMessage.toString()).isEqualTo(receivedMessage);
    }

    @Test
    public void createStockResponseMessage() throws Exception {
        String receivedMessage = "Team5_Team6_1_05_05_Team5_10_5";

        StockResponseMessage parsedMessage = (StockResponseMessage) MessageParser.createMessage(receivedMessage);
        assertThat(receivedMessage.toString()).isEqualTo(receivedMessage);
    }

    @Test
    public void createPrepaymentInfoMessage() throws Exception {
        String receivedMessage = "Team5_Team6_2_17_2_test105te";

        PrepaymentInfoMessage parsedMessage = (PrepaymentInfoMessage) MessageParser.createMessage(receivedMessage);
        assertThat(receivedMessage.toString()).isEqualTo(receivedMessage);
    }

    @Test
    public void createSaleRequestMessage() throws Exception {
        String receivedMessage = "Team5_Team6_3_15_1";

        SaleRequestMessage parsedMessage = (SaleRequestMessage) MessageParser.createMessage(receivedMessage);
        assertThat(receivedMessage.toString()).isEqualTo(receivedMessage);
    }

    @Test
    public void createSaleResponseMessage() throws Exception {
        String receivedMessage = "Team5_Team6_4_20_Team5_5_10";

        SaleResponseMessage parsedMessage = (SaleResponseMessage) MessageParser.createMessage(receivedMessage);
        assertThat(receivedMessage.toString()).isEqualTo(receivedMessage);
    }
}
