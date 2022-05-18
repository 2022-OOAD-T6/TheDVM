package dvm.network.message;

import org.junit.jupiter.api.Test;

import static dvm.network.message.MessageType.*;
import static org.assertj.core.api.Assertions.*;

/**
 * 현 DVM에서 보내는 메세지 테스트
 */
class MessageTest {

    @Test
    public void StockRequestMessage(){
        String itemCode = "15";
        int quantity = 8;

        StockRequestMessage sendingMessage = new StockRequestMessage(itemCode, quantity);
        StockRequestMessage checkMessage = new StockRequestMessage(Message.getCurrentId(),"0", STOCK_REQUEST.toString(), itemCode, quantity);
        assertThat(sendingMessage.getMessageType()).isEqualTo(STOCK_REQUEST);
        assertThat(sendingMessage).usingRecursiveComparison().isEqualTo(checkMessage);
    }

    @Test
    public void StockResponseMessage(){
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
    public void PrepaymentInfoMessage(){
        String dstId = "Team5";
        String itemCode = "05";
        int quantity = 10;
        String verificationCode = "test12304test"; // 맞는 인증코드인지는 message쪽에서 확인은 안함. 받은 대로 저장만

        PrepaymentInfoMessage sendingMessage = new PrepaymentInfoMessage(dstId, itemCode, quantity, verificationCode);
        PrepaymentInfoMessage checkMessage = new PrepaymentInfoMessage(Message.getCurrentId(),dstId, PREPAYMENT_INFO.toString(), itemCode, quantity, verificationCode);

        assertThat(sendingMessage.getMessageType()).isEqualTo(PREPAYMENT_INFO);
        assertThat(sendingMessage).usingRecursiveComparison().isEqualTo(checkMessage);
    }

    @Test
    public void SaleRequestMessage(){
        String itemCode = "01";
        int quantity = 5;

        SaleRequestMessage sendingMessage = new SaleRequestMessage(itemCode, quantity);
        SaleRequestMessage checkMessage = new SaleRequestMessage(Message.getCurrentId(), "0", SALE_REQUEST.toString(), itemCode, quantity);

        assertThat(sendingMessage.getMessageType()).isEqualTo(SALE_REQUEST);
        assertThat(sendingMessage).usingRecursiveComparison().isEqualTo(checkMessage);
    }

    @Test
    public void SaleResponseMessage(){
        String dstId = "Team1";
        String itemCode = "02";

        SaleResponseMessage sendingMessage = new SaleResponseMessage(dstId, itemCode);
        SaleResponseMessage checkMessage = new SaleResponseMessage(Message.getCurrentId(), dstId, SALE_RESPONSE.toString(), itemCode,
                Message.getCurrentId(), Message.getCurrentX(), Message.getCurrentY());

        assertThat(sendingMessage.getMessageType()).isEqualTo(SALE_RESPONSE);
        assertThat(sendingMessage).usingRecursiveComparison().isEqualTo(checkMessage);
    }

}
