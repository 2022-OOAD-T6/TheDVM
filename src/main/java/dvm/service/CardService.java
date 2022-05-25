package dvm.service;

import dvm.partners.CardCompany;

public class CardService {


    public CardService(CardCompany cardCompany) {
        this.cardCompany = cardCompany;
    }

    private String curCardNum;

    private final CardCompany cardCompany;

    public boolean saveCardNum(String cardNum) {
        if (cardCompany.isValid(cardNum)) {
            this.curCardNum = cardNum;
            return true;
        } else {
            return false;
        }
    }

    public boolean pay(int price) {
        // TODO implement here
        if (cardCompany.pay(this.curCardNum, price)) {
            return true;
        } else
            return false;
    }
}