package dvm.partners;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 외부 카드사
 */
public class CardCompany {
    private final Logger logger = Logger.getGlobal();

    private final HashMap<String, Integer> registeredCards = new HashMap<>();

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
        for (Map.Entry<String, Integer> cardEntry : registeredCards.entrySet()) {
            logger.info("카드번호: " + cardEntry.getKey() + " | 잔고: " + cardEntry.getValue());
        }
        System.out.println("--------------------------------------------------");
    }

    private static class CardCompanyHelper{
        private static final CardCompany cardCompany = new CardCompany();
    }

    public static CardCompany getInstance() {
        return CardCompanyHelper.cardCompany;
    }

    public boolean isValid(String cardNum) {
        //존재하는 카드일 경우
        return this.registeredCards.containsKey(cardNum);
    }

    public boolean pay(String cardNum, int price) {
        Integer cardValue = this.registeredCards.get(cardNum);
        if (cardValue == null || cardValue < price) {
            return false;
        } else {
            logger.info("현재 카드 잔액 " + cardValue + "원 | " + price + " 결제 가능. 결제합니다.");
            this.registeredCards.put(cardNum, cardValue - price);
            return true;
        }
    }
}