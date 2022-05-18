package dvm;

import dvm.repository.ItemRepository;
import dvm.service.ItemService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");


        // item testing
        ItemRepository ir = new ItemRepository();
        ItemService service=new ItemService(ir);

        String itemCode = "01";
        int qty = 111;
        System.out.println(service.isEnough(itemCode, qty));
        System.out.println(service.getItemPrice(itemCode));
//        System.out.println(service.updateStock(itemCode, qty));
        System.out.println(service.getItemCount(itemCode));
    }
}