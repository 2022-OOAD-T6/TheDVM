package dvm.service;

import dvm.repository.ItemRepository;

/**
 * 
 */
public class ItemService {

    /**
     * Default constructor
     */
    public ItemService() {
    }

    /**
     * 
     */
    private ItemRepository itemRepository;

    /**
     * @param itemCode 
     * @param quantity 
     * @return
     */
    public boolean isEnough(String itemCode, int quantity) {
        // TODO implement here
        return false;
    }

    /**
     * @param itemCode 
     * @return
     */
    public int getItemPrice(String itemCode) {
        // TODO implement here
        return 0;
    }

    /**
     * @param itemCode 
     * @param quantity 
     * @return
     */
    public void updateStock(String itemCode, int quantity) {
        // TODO implement here
    }

    /**
     * @param itemCode 
     * @return
     */
    public int getItemCount(String itemCode) {
        // TODO implement here
        return 0;
    }

}