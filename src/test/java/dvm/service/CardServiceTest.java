package dvm.service;

import dvm.repository.PrepaymentRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardServiceTest {

    String curCardNum = "02011370";
    CardService cardService = new CardService(curCardNum, new CardCompany());
    @Test
    void saveCardNum() {

    }

    @Test
    void pay() {
    }
}