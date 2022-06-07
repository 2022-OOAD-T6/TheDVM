package dvm.partners;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * 외부 카드사
 */
public class CardCompany {
    private final Logger logger = Logger.getGlobal();

    public CardCompany() {
        registeredCards.put("01711374", 1000);
        registeredCards.put("01814119", 2000);
        registeredCards.put("01911167", 3000);
        registeredCards.put("02011370", 4000);
        registeredCards.put("02111111", 5000);
        registeredCards.put("02222222", 6000);
        registeredCards.put("02333333", 7000);
        registeredCards.put("02444444", 8000);

        // 디버깅 위해 카드 번호 로그 남김
        System.out.println("----------------현재 등록된 카드 정보------------------");
        for (String cardNumber : registeredCards.keySet()) {
            System.out.println("카드번호: " + cardNumber + " | 잔고: " + registeredCards.get(cardNumber));
        }
        System.out.println("--------------------------------------------------");
    }

    private final HashMap<String, Integer> registeredCards = new HashMap<>();

    public boolean isValid(String cardNumber) {
        // TODO implement here
        //존재하는 카드일 경우
        return this.registeredCards.containsKey(cardNumber);
    }

    public boolean pay(String cardNumber, int price) {
        Integer cardValue = this.registeredCards.get(cardNumber);
        if (cardValue == null || cardValue < price) {
            return false;
        } else {
            logger.info("현재 카드 잔액 " + cardValue + "원 | " + price + " 결제 가능. 결제합니다.");
            this.registeredCards.put(cardNumber, cardValue - price);
            return true;
        }
    }
}