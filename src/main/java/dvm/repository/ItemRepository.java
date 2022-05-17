package dvm.repository;

import dvm.domain.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 20220517 문지영
 */
public class ItemRepository {

    public ItemRepository() {
        items= new ArrayList<>(20);
        items.add(new Item("콜라", "01", 100));
        items.add(new Item("사이다", "02", 100));
        items.add(new Item("녹차", "03", 100));
        items.add(new Item("홍차", "04", 100));
        items.add(new Item("밀크티", "05", 100));
        items.add(new Item("탄산수", "06", 100));
        items.add(new Item("보리차", "07", 100));
        items.add(new Item("캔커피", "08", 100));
        items.add(new Item("물", "09", 100));
        items.add(new Item("에너지드링크", "10", 100));
        items.add(new Item("바닷물", "11", 100));
        items.add(new Item("식혜", "12", 100));
        items.add(new Item("아이스티", "13", 100));
        items.add(new Item("딸기주스", "14", 100));
        items.add(new Item("오렌지주스", "15", 100));
        items.add(new Item("포도주스", "16", 100));
        items.add(new Item("이온음료", "17", 100));
        items.add(new Item("아메리카노", "18", 100));
        items.add(new Item("핫초코", "19", 100));
        items.add(new Item("카페라떼", "20", 100));

        stock= new ConcurrentHashMap<>(7);
        // 임시 배정
        stock.put(items.get(0), 100);
        stock.put(items.get(1), 100);
        stock.put(items.get(2), 100);
        stock.put(items.get(3), 100);
        stock.put(items.get(4), 100);
        stock.put(items.get(5), 100);
        stock.put(items.get(6), 100);
    }

    private List<Item> items;// 모든 음료 20개

    private ConcurrentHashMap<Item, Integer> stock;// 우리 자판기 음료 7개

    /**
    * 우리 자판기에 있는 음료 수량 리턴
    * key가 없으면 return null
    * */
    public int count(String itemCode) {
        // TODO implement here
        return stock.get(itemCode);
    }

    /**
     * itemCode로 Item 객체 리턴
     * 없으면 return null
     * */
    public Item findItem(String itemCode) {
        // TODO implement here
        for (Item item : items) {
            if(item.getItemCode() == itemCode) {
                return item;
            }
        }
        return null;
    }

    /**
     * 정상적으로 결제 후에만 재고 변경 가능
     * 결제한 음료 수량만큼 재고 빼기
     * */
    public void update(String itemCode, int quantity) {
        // TODO implement here
//        Item item = findItem(itemCode);
        int oldQnt = stock.get(itemCode);
        stock.put(findItem(itemCode), oldQnt-quantity);
    }

}