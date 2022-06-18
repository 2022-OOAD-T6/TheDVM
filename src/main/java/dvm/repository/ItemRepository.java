package dvm.repository;

import dvm.domain.Item;
import dvm.util.Observer;
import dvm.util.Subject;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;


public class ItemRepository implements Subject {

    private final List<Item> items = new ArrayList<>(
            Arrays.asList(new Item("01", "콜라", 1000),
                    new Item("02", "사이다", 2000),
                    new Item("03", "녹차", 3000),
                    new Item("04", "홍차", 100),
                    new Item("05", "밀크티", 100),
                    new Item("06", "탄산수", 100),
                    new Item("07", "보리차", 100),
                    new Item("08", "캔커피", 100),
                    new Item("09", "물", 1500),
                    new Item("10", "에너지드링크", 100),
                    new Item("11", "바닷물", 100),
                    new Item("12", "식혜", 100),
                    new Item("13", "아이스티", 100),
                    new Item("14", "딸기주스", 100),
                    new Item("15", "오렌지주스", 100),
                    new Item("16", "포도주스", 100),
                    new Item("17", "이온음료", 100),
                    new Item("18", "아메리카노", 100),
                    new Item("19", "핫초코", 100),
                    new Item("20", "카페라떼", 100)
            )
    );// 모든 음료 20개
    private final ConcurrentHashMap<String, Integer> stock;// 우리 자판기 음료 7개

    private final List<Observer> observers =  new ArrayList<>();

    private final Logger logger = Logger.getGlobal();

    private ItemRepository() { // singleton 위해 생성자 접근 막음
        stock = new ConcurrentHashMap<>();
        try {
            // resources에 properties/?.properties 파일들 읽어서 세팅 -> 매번 빌드 안하기 위함
            Properties p = new Properties();
            p.load(new FileReader("src/main/resources/properties/stock.properties"));
            if (p.size() != 7) {
                logger.warning("자판기 음료 개수는 7개여야 합니다.");
                throw new Exception();
            }
            for (Object o : p.keySet()) {
                String key = (String) o;
                stock.put((String) key, Integer.parseInt(p.getProperty(key)));
            }
        } catch (Exception e) {
            logger.warning("stock.properties 이상. 기본 세팅으로 세팅합니다.");
            stock.put("01", 2);
            stock.put("02", 2);
            stock.put("03", 2);
            stock.put("04", 2);
            stock.put("05", 2);
            stock.put("06", 2);
            stock.put("10", 10);
        }
        printCurrentStock();
    }

    private static class ItemRepositoryHelper {
        private static final ItemRepository itemRepository = new ItemRepository();
    }

    public static ItemRepository getInstance() {
        return ItemRepositoryHelper.itemRepository;
    }


    /**
     * 디버깅용 - 현재 stock 상태 출력
     */
    private void printCurrentStock() {
        System.out.println("-------------------현재 재고 정보--------------------");
        for (String s : stock.keySet()) {
            System.out.println("item code: " + s + " | count: " + stock.get(s));
        }
        System.out.println("--------------------------------------------------");
    }


    /**
     * 우리 자판기에 있는 음료 수량 리턴
     * key가 없으면 return 0
     */
    public int count(String itemCode) {
        if (stock.get(itemCode) != null) {
            return stock.get(itemCode);
        } else {
            return 0;
        }
    }

    /**
     * itemCode로 Item 객체 탐색
     * Optional로 감싸서 리턴
     */
    public Optional<Item> findItem(String itemCode) {
        return items.stream()
                .filter(item -> item.getItemCode().equals(itemCode))
                .findAny();
    }

    /**
     * 모든 아이템 정보 리스트에 담아 리턴
     */
    public List<Item> findAllItems() {
        return new ArrayList<>(items);
    }

    /**
     * 우리가 팔고있는 아이템을 새로운 리스트에 담아 리턴
     */
    public List<Item> findMyItems() {
        ArrayList<Item> ourItems = new ArrayList<>();
        stock.keySet()
                .forEach(key -> findItem(key).ifPresent(ourItems::add));

        return ourItems;
    }

    /**
     * 정상적으로 결제 후에만 재고 변경 가능
     * 결제한 음료 수량만큼 재고 빼기
     */
    public void update(String itemCode, int quantity) {
        int oldQty = stock.get(itemCode);
        stock.put(itemCode, oldQty + quantity);

        notifyObservers(itemCode, oldQty + quantity);
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(String itemCode, int quantity) {
        observers.forEach(observer -> observer.updateObserver(itemCode, quantity));
    }
}