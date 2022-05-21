package dvm.partners;

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

    private final HashMap<String,Integer> registeredCards = new HashMap<>();

    public boolean isValid(String cardNum) {
        // TODO implement here
        //존재하는 카드일 경우
        return this.registeredCards.containsKey(cardNum) == true;
    }

    public boolean pay(String cardNum, int price) {
        Integer cardValue = this.registeredCards.get(cardNum);
        if(cardValue == null || cardValue < price){
            return false;
        }else{
            cardValue -= price;
            return true;
        }
    }
}