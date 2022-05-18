package dvm.service;

import java.util.HashMap;

/**
 *  외부 카드사
 */
public class CardCompany {


    public CardCompany() {
        registeredCards.put("01711374",1000);
        registeredCards.put("01814119",2000);
        registeredCards.put("01911167",3000);
        registeredCards.put("02011370",4000);
        registeredCards.put("02111111",5000);
        registeredCards.put("02222222",6000);
        registeredCards.put("02333333",7000);
        registeredCards.put("02444444",8000);
    }

    private HashMap<String,Integer> registeredCards;

    public boolean isValid(String cardNum) {
        // TODO implement here
        if(this.registeredCards.containsKey(cardNum) == true){      //존재하는 카드일 경우
            return true;
        }else
            return false;
    }

    public boolean pay(String cardNum, int price) {
        // TODO implement here
        int cardValue = this.registeredCards.get(cardNum);
        if(cardValue >= price){
            cardValue -= price;
            registeredCards.put(cardNum,cardValue);
            return true;
        }else{
            return false;
        }
    }

    public void Operation1() {
        // TODO implement here
    }

    public void Operation2() {
        // TODO implement here
    }

}