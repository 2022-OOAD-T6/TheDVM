package dvm.network.message;

import org.junit.jupiter.api.Test;

import static dvm.network.message.MessageType.PREPAYMENT_INFO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PrepaymentInfoMessageTest {

    @Test
    void PrepaymentInfoMessage(){
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
    void testToString() {
        String dstId = "Team5";
        String itemCode = "05";
        int quantity = 10;
        String verificationCode = "test12304test"; // 맞는 인증코드인지는 message쪽에서 확인은 안함. 받은 대로 저장만

        Message.setCurrentId("Team6");

        PrepaymentInfoMessage sendingMessage = new PrepaymentInfoMessage(dstId, itemCode, quantity, verificationCode);
        PrepaymentInfoMessage checkMessage = new PrepaymentInfoMessage(Message.getCurrentId(),dstId, PREPAYMENT_INFO.toString(), itemCode, quantity, verificationCode);

        assertThat(sendingMessage.toString()).isEqualTo("Team6_Team5_2_05_10_test12304test");
        assertThat(checkMessage.toString()).isEqualTo("Team6_Team5_2_05_10_test12304test");
    }

}