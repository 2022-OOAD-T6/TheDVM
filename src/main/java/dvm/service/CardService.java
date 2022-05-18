package dvm.service;

import java.util.HashMap;

/**
 * 
 */
public class CardService {

    /**
     * Default constructor
     */
    public CardService() {

    }
    /**
     * 
     */
    private String curCardNum;

    /**
     * 
     */
    private CardCompany cardCompany;

    /**
     * @param cardNum 
     * @return
     */
    public boolean saveCardNum(String cardNum) {
        // TODO implement here
        if(cardNum.length()==8) {
            this.curCardNum = cardNum;
            return true;
        }else{
            return false;
        }
    }

    /**
     * @param price 
     * @return
     */
    public boolean pay(int price) {
        // TODO implement here
        cardCompany.pay(this.curCardNum,price);
        return false;
    }



}