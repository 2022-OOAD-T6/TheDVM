package dvm.network;

import GsonConverter.Serializer;
import Model.Message;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.assertj.core.api.Assertions.assertThat;

class MessageFactoryTest {

    private final Serializer serializer = new Serializer();

    @Test
    void createStockRequestMessage() {
        MessageFactory.setCurrentId("Team6");
        MessageFactory.setCurrentX(10);
        MessageFactory.setCurrentY(15);

        Message message = MessageFactory.createStockRequestMessage("02", 10);
        String messageTojson = "{\"srcId\":\"Team6\",\"dstID\":\"0\",\"msgType\":\"StockCheckRequest\",\"msgDescription\":{\"itemCode\":\"02\",\"itemNum\":10,\"dvmXCoord\":0,\"dvmYCoord\":0}}";
        assertThat(serializer.message2Json(message)).isEqualTo(messageTojson);
    }

    @Test
    void createStockResponseMessage() {
        MessageFactory.setCurrentId("Team6");
        MessageFactory.setCurrentX(10);
        MessageFactory.setCurrentY(15);

        Message message = MessageFactory.createStockResponseMessage("Team2", "05", 10);
        String messageTojson = "{\"srcId\":\"Team6\",\"dstID\":\"Team2\",\"msgType\":\"StockCheckResponse\",\"msgDescription\":{\"itemCode\":\"05\",\"itemNum\":10,\"dvmXCoord\":10,\"dvmYCoord\":15}}";
        assertThat(serializer.message2Json(message)).isEqualTo(messageTojson);
    }

    @Test
    void createPrepaymentCheckMessage() {
        MessageFactory.setCurrentId("Team6");
        MessageFactory.setCurrentX(10);
        MessageFactory.setCurrentY(15);

        Message message = MessageFactory.createPrepaymentCheckMessage("Team1", "03", 15, "525a");
        String messageTojson = "{\"srcId\":\"Team6\",\"dstID\":\"Team1\",\"msgType\":\"PrepaymentCheck\",\"msgDescription\":{\"itemCode\":\"03\",\"itemNum\":15,\"dvmXCoord\":0,\"dvmYCoord\":0,\"authCode\":\"525a\"}}";
        assertThat(serializer.message2Json(message)).isEqualTo(messageTojson);
    }

    @Test
    void createSaleRequestMessage() {
        MessageFactory.setCurrentId("Team6");
        MessageFactory.setCurrentX(10);
        MessageFactory.setCurrentY(15);

        Message message = MessageFactory.createSaleRequestMessage("02", 10);
        String messageTojson = "{\"srcId\":\"Team6\",\"dstID\":\"0\",\"msgType\":\"SalesCheckRequest\",\"msgDescription\":{\"itemCode\":\"02\",\"itemNum\":10,\"dvmXCoord\":0,\"dvmYCoord\":0}}";
        assertThat(serializer.message2Json(message)).isEqualTo(messageTojson);

    }

    @Test
    void createSaleResponseMessage() {
        MessageFactory.setCurrentId("Team6");
        MessageFactory.setCurrentX(10);
        MessageFactory.setCurrentY(15);

        Message message = MessageFactory.createSaleResponseMessage("Team1", "10");
        String messageTojson = "{\"srcId\":\"Team6\",\"dstID\":\"Team1\",\"msgType\":\"SalesCheckResponse\",\"msgDescription\":{\"itemCode\":\"10\",\"itemNum\":0,\"dvmXCoord\":10,\"dvmYCoord\":15}}";
        assertThat(serializer.message2Json(message)).isEqualTo(messageTojson);
    }

    @Test
    void sortMessages() {
        MessageFactory.setCurrentId("Team6");
        MessageFactory.setCurrentX(10);
        MessageFactory.setCurrentY(15);

        Vector<Message> messages = new Vector<>();

        Message m1 = new Message();
        m1.setSrcId("Team1");
        Message.MessageDescription messageDescription1 = new Message.MessageDescription();
        messageDescription1.setDvmXCoord(99);
        messageDescription1.setDvmYCoord(99);
        m1.setMsgDescription(messageDescription1);
        messages.add(m1);
        assertThat(m1.getSrcId()).isEqualTo("Team1");

        Message m2 = new Message();
        m2.setSrcId("Team2");
        Message.MessageDescription messageDescription2 = new Message.MessageDescription();
        messageDescription2.setDvmXCoord(20);
        messageDescription2.setDvmYCoord(15);
        m2.setMsgDescription(messageDescription2);
        messages.add(m2);
        assertThat(m2.getSrcId()).isEqualTo("Team2");

        Message m3 = new Message();
        m3.setSrcId("Team4");
        Message.MessageDescription messageDescription3 = new Message.MessageDescription();
        messageDescription3.setDvmXCoord(12);
        messageDescription3.setDvmYCoord(15);
        m3.setMsgDescription(messageDescription3);
        messages.add(m3);
        assertThat(m3.getSrcId()).isEqualTo("Team4");

        Message m4 = new Message();
        m4.setSrcId("Team5");
        Message.MessageDescription messageDescription4 = new Message.MessageDescription();
        messageDescription4.setDvmXCoord(30);
        messageDescription4.setDvmYCoord(15);
        m4.setMsgDescription(messageDescription4);
        messages.add(m4);
        assertThat(m4.getSrcId()).isEqualTo("Team5");

        Message m5 = new Message();
        m5.setSrcId("Team3");
        Message.MessageDescription messageDescription5 = new Message.MessageDescription();
        messageDescription5.setDvmXCoord(12);
        messageDescription5.setDvmYCoord(15);
        m5.setMsgDescription(messageDescription5);
        messages.add(m5);
        assertThat(m5.getSrcId()).isEqualTo("Team3");

        MessageFactory.sortMessages(messages);

        assertThat(messages.get(0).getSrcId()).isEqualTo("Team3");
        assertThat(messages.get(1).getSrcId()).isEqualTo("Team4");
        assertThat(messages.get(2).getSrcId()).isEqualTo("Team2");
        assertThat(messages.get(3).getSrcId()).isEqualTo("Team5");
        assertThat(messages.get(4).getSrcId()).isEqualTo("Team1");
    }

    @Test
    void sortMessages2(){
        MessageFactory.setCurrentId("Team6");
        MessageFactory.setCurrentX(10);
        MessageFactory.setCurrentY(15);

        Vector<Message> messages = new Vector<>();

        Message m1 = MessageFactory.createSaleResponseMessage("Team6", "05");
        m1.setSrcId("Team5");
        m1.getMsgDescription().setDvmXCoord(35);
        m1.getMsgDescription().setDvmYCoord(35);
        messages.add(m1);

        Message m2 = MessageFactory.createSaleResponseMessage("Team6", "05");
        m2.setSrcId("Team4");
        m2.getMsgDescription().setDvmXCoord(30);
        m2.getMsgDescription().setDvmYCoord(30);
        messages.add(m2);

        Message m3 = MessageFactory.createSaleResponseMessage("Team6", "05");
        m3.setSrcId("Team3");
        m3.getMsgDescription().setDvmXCoord(25);
        m3.getMsgDescription().setDvmYCoord(25);
        messages.add(m3);

        Message m4 = MessageFactory.createSaleResponseMessage("Team6", "05");
        m4.setSrcId("Team2");
        m4.getMsgDescription().setDvmXCoord(20);
        m4.getMsgDescription().setDvmYCoord(20);
        messages.add(m4);

        Message m5 = MessageFactory.createSaleResponseMessage("Team6", "05");
        m5.setSrcId("Team1");
        m5.getMsgDescription().setDvmXCoord(15);
        m5.getMsgDescription().setDvmYCoord(15);
        messages.add(m5);

        MessageFactory.sortMessages(messages);

        assertThat(messages.get(0).getSrcId()).isEqualTo("Team1");
        assertThat(messages.get(1).getSrcId()).isEqualTo("Team2");
        assertThat(messages.get(2).getSrcId()).isEqualTo("Team3");
        assertThat(messages.get(3).getSrcId()).isEqualTo("Team4");
        assertThat(messages.get(4).getSrcId()).isEqualTo("Team5");
    }

    @Test
    void sortMessages3(){
        MessageFactory.setCurrentId("Team6");
        MessageFactory.setCurrentX(10);
        MessageFactory.setCurrentY(15);

        Vector<Message> messages = new Vector<>();

        Message m1 = MessageFactory.createSaleResponseMessage("Team6", "05");
        m1.setSrcId("Team5");
        m1.getMsgDescription().setDvmXCoord(15);
        m1.getMsgDescription().setDvmYCoord(15);
        messages.add(m1);

        Message m2 = MessageFactory.createSaleResponseMessage("Team6", "05");
        m2.setSrcId("Team4");
        m2.getMsgDescription().setDvmXCoord(15);
        m2.getMsgDescription().setDvmYCoord(15);
        messages.add(m2);

        Message m3 = MessageFactory.createSaleResponseMessage("Team6", "05");
        m3.setSrcId("Team3");
        m3.getMsgDescription().setDvmXCoord(15);
        m3.getMsgDescription().setDvmYCoord(15);
        messages.add(m3);

        Message m4 = MessageFactory.createSaleResponseMessage("Team6", "05");
        m4.setSrcId("Team2");
        m4.getMsgDescription().setDvmXCoord(15);
        m4.getMsgDescription().setDvmYCoord(15);
        messages.add(m4);

        Message m5 = MessageFactory.createSaleResponseMessage("Team6", "05");
        m5.setSrcId("Team1");
        m5.getMsgDescription().setDvmXCoord(15);
        m5.getMsgDescription().setDvmYCoord(15);
        messages.add(m5);

        MessageFactory.sortMessages(messages);

        assertThat(messages.get(0).getSrcId()).isEqualTo("Team1");
        assertThat(messages.get(1).getSrcId()).isEqualTo("Team2");
        assertThat(messages.get(2).getSrcId()).isEqualTo("Team3");
        assertThat(messages.get(3).getSrcId()).isEqualTo("Team4");
        assertThat(messages.get(4).getSrcId()).isEqualTo("Team5");
    }
}