package dvm.service;

import dvm.repository.ItemRepository;

/**
 * 20220517 문지영
 */
public class ItemService {

    public ItemService() {
    }

    private ItemRepository itemRepository;

    public boolean isEnough(String itemCode, int quantity) {
        // TODO implement here
        return false;
    }

    public int getItemPrice(String itemCode) {
        // TODO implement here
        return 0;
    }

    public void updateStock(String itemCode, int quantity) {
        // TODO implement here
    }

    public int getItemCount(String itemCode) {
        // TODO implement here
        return 0;
    }

}