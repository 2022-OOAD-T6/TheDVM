package dvm.service;

import dvm.partners.CardCompany;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CardServiceTest {

    String myNum = "01711374"; // 있는 카드
    CardService cardService = new CardService(new CardCompany());
    CardCompany cardCompany = new CardCompany();
    @Test
    void saveCardNum() {
        boolean result1 = cardService.saveCardNumber(myNum);
        assertThat(result1).isTrue();
        boolean result2 = cardService.saveCardNumber("000099");
        assertThat(result2).isFalse();
    }

    @Test
    void pay() {
        cardService.saveCardNumber(myNum);
        assertTrue(cardService.pay(1000));
        assertThat(cardService.pay(9999)).isFalse();
    }
}