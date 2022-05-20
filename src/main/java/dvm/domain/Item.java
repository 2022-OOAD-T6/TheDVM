package dvm.domain;

/**
 * 20220517 MJY
 */
public class Item {

    private String itemCode; // 음료코드
    private String name;// 음료이름
    private int price; //음료가격

    public Item(String itemCode, String name, int price) {
        this.itemCode=itemCode;
        this.name=name;
        this.price=price;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}