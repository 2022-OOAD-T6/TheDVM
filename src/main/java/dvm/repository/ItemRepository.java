package dvm.repository;

import dvm.domain.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * 20220517 MJY
 */
public class ItemRepository {

    private final Logger logger = Logger.getGlobal();
    private final List<Item> items;// 모든 음료 20개
    private final ConcurrentHashMap<String, Integer> stock;// 우리 자판기 음료 7개

    public ItemRepository() {
        items = new ArrayList<>(20);
        items.add(new Item("01", "콜라", 100));
        items.add(new Item("02", "사이다", 100));
        items.add(new Item("03", "녹차", 100));
        items.add(new Item("04", "홍차", 100));
        items.add(new Item("05", "밀크티", 100));
        items.add(new Item("06", "탄산수", 100));
        items.add(new Item("07", "보리차", 100));
        items.add(new Item("08", "캔커피", 100));
        items.add(new Item("09", "물", 100));
        items.add(new Item("10", "에너지드링크", 100));
        items.add(new Item("11", "바닷물", 100));
        items.add(new Item("12", "식혜", 100));
        items.add(new Item("13", "아이스티", 100));
        items.add(new Item("14", "딸기주스", 100));
        items.add(new Item("15", "오렌지주스", 100));
        items.add(new Item("16", "포도주스", 100));
        items.add(new Item("17", "이온음료", 100));
        items.add(new Item("18", "아메리카노", 100));
        items.add(new Item("19", "핫초코", 100));
        items.add(new Item("20", "카페라떼", 100));

        stock = new ConcurrentHashMap<>(7);
        // 임시 배정
        stock.put("01", 100);
        stock.put("02", 100);
        stock.put("03", 100);
        stock.put("04", 100);
        stock.put("05", 100);
        stock.put("06", 100);
        stock.put("07", 100);
    }

    /**
     * 우리 자판기에 있는 음료 수량 리턴
     * key가 없으면 return null
     */
    public int count(String itemCode) {
        // TODO implement here
//        System.out.println(stock.get(findItem(itemCode)));
        if (stock.get(itemCode) != null){
            return stock.get(itemCode);
        }else{
            logger.warning("존재하지 않는 음료 코드입니다.");
            return 0;
        }
    }

    /**
     * itemCode로 Item 객체 리턴
     * 없으면 return null
     */
    public Item findItem(String itemCode) {
        // TODO implement here
        for (Item item : items) {
            if (item.getItemCode().equals(itemCode)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 모든 아이템 리턴
     */
    public List<Item> findAllItems() {
        return items;
    }

    /**
     * 정상적으로 결제 후에만 재고 변경 가능
     * 결제한 음료 수량만큼 재고 빼기
     */
    public void update(String itemCode, int quantity) {
        // TODO implement here
//        Item item = findItem(itemCode);
        int oldQty = stock.get(itemCode);
        stock.put(itemCode, oldQty + quantity);
    }

}