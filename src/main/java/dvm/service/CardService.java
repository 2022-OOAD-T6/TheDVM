package dvm.service;

import dvm.partners.CardCompany;

public class CardService {


    public CardService(CardCompany cardCompany) {
        this.cardCompany = cardCompany;
    }

    private String currentCardNumber;

    private final CardCompany cardCompany;

    public boolean saveCardNumber(String cardNumber) {
        if (cardCompany.isValid(cardNumber)) {
            this.currentCardNumber = cardNumber;
            return true;
        } else {
            return false;
        }
    }

    public boolean pay(int price) {
        // TODO implement here
        if (cardCompany.pay(this.currentCardNumber, price)) {
            return true;
        } else
            return false;
    }
}