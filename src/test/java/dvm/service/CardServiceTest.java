package dvm.service;

import dvm.partners.CardCompany;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardServiceTest {

    String myNum = "02011370";
    CardService cardService = new CardService(myNum, new CardCompany());
    CardCompany cardCompany = new CardCompany();
    @Test
    void saveCardNum() {
        cardService.saveCardNum(myNum);

    }

    @Test
    void pay() {

        assertTrue(cardService.pay(1000));
    }
}