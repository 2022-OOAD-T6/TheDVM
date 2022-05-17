package dvm.repository;

import java.util.HashMap;
import java.util.List;

/**
 * 
 */
public class ItemRepository {

    /**
     * Default constructor
     */
    public ItemRepository() {
    }

    /**
     * 
     */
    private List<Item> items;

    /**
     * 
     */
    private HashMap<Item, int> stock;

    /**
     * @param itemCode 
     * @return
     */
    public int count(String itemCode) {
        // TODO implement here
        return 0;
    }

    /**
     * @param itemCode 
     * @return
     */
    public Item findItem(String itemCode) {
        // TODO implement here
        return null;
    }

    /**
     * @param itemCode 
     * @param quantity 
     * @return
     */
    public void update(String itemCode, int quantity) {
        // TODO implement here
        return null;
    }

}