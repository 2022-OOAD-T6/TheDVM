package dvm.service;

import dvm.repository.ItemRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemServiceTest {
    String itemCode = "01";
    int qty = 111;

    @Test
    void isEnough() {
        ItemRepository ir = new ItemRepository();
        ItemService is = new ItemService(ir);
        assertEquals(is.isEnough(itemCode, qty), false);
    }

    @Test
    void getItemPrice() {
        ItemRepository ir = new ItemRepository();
        ItemService is = new ItemService(ir);
        //assertEquals(is.getItemPrice(itemCode), 100);
    }

    @Test
    void updateStock() {
        ItemRepository ir = new ItemRepository();
        ItemService is = new ItemService(ir);
        int oldQty = ir.count(itemCode);
        is.updateStock(itemCode, qty);
        int newQty=ir.count(itemCode);
        assertEquals(oldQty+111, newQty);
    }

    @Test
    void getItemCount() {
        // ItemRepository ir = new ItemRepository();
        // ItemService is = new ItemService(ir);
        // assertEquals(is.getItemCount(itemCode), 100);
    }
}