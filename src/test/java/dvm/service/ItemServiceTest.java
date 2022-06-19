package dvm.service;

import dvm.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemServiceTest {

    ItemRepository itemRepository;
    ItemService itemService;

    @BeforeEach
    void beforeEach() {
        itemRepository = new ItemRepository();
        itemService = new ItemService(itemRepository);
    }

    @Test
    void isEnough() {
        String itemCode = "01";
        int qty = 111;
        assertFalse(itemService.isEnough(itemCode, qty));
    }

    @Test
    void getItemPrice() {
        String itemCode = "01";
        assertEquals(itemService.getItemPrice(itemCode), 1000);
    }

    @Test
    void updateStock() {
        String itemCode = "01";
        int oldQuantity = itemRepository.count(itemCode);
        int updateCount = 111;

        itemService.updateStock(itemCode, updateCount);
        int newQuantity=itemRepository.count(itemCode);
        assertEquals(oldQuantity+111, newQuantity);
    }

    @Test
    void getItemCount() {
        String itemCode = "01";
         assertEquals(itemService.getItemCount(itemCode), 2);
    }
}