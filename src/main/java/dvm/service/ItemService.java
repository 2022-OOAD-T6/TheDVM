package dvm.service;

import dvm.domain.Item;
import dvm.repository.ItemRepository;

/**
 * 20220517 MJY
 */
public class ItemService {

    private ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * 선택한 음료 수량이 충분하면 true
     * 아니면 false
     */
    public boolean isEnough(String itemCode, int quantity) {
        // TODO implement here
        if(itemRepository.count(itemCode) >= quantity)
            return true;
        else
            return false;
    }

    /**
     * 음료 가격 리턴
     * 여기서 Item 객체 필요?
     */
    public int getItemPrice(String itemCode) {
        // TODO implement here
        Item item = itemRepository.findItem(itemCode);
        return item.getPrice();
    }

    /**
     * 정상적으로 결제 후 수행 가능
     * 음료 재고 수량 빼기
     */
    public boolean updateStock(String itemCode, int quantity) {
        // TODO implement here
        if(itemRepository.count(itemCode)+quantity >= 0 &&
                itemRepository.count(itemCode)+quantity <= 999){
            itemRepository.update(itemCode, quantity);
            return true;
        }else
            return false;
    }

    /**
     * 재고 정보 확인 프로토콜이 들어오면 수행
     * 음료 수량 리턴
     */
    public int getItemCount(String itemCode) {
        // TODO implement here
        return itemRepository.count(itemCode);
    }

}