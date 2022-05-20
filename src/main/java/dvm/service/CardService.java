package dvm.service;

import java.util.HashMap;

public class CardService {


    public CardService(String curCardNum, CardCompany cardCompany) {
        this.curCardNum = curCardNum;
        this. cardCompany = cardCompany;
    }

    private String curCardNum;

    private final CardCompany cardCompany;

    public boolean saveCardNum(String cardNum) {
        // TODO implement here
        if(cardNum.length()==8) {
            this.curCardNum = cardNum;
            return true;
        }else{
            return false;
        }
    }

    public boolean pay(int price) {
        // TODO implement here
        return cardCompany.pay(this.curCardNum, price) == true;
    }

}