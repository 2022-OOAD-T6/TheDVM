package dvm;

import dvm.service.ItemService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");


        // item testing
        ItemService service=new ItemService();

        String itemCode = "01";
        int qty = 111;
        System.out.println(service.isEnough(itemCode, qty));
        System.out.println(service.getItemPrice(itemCode));
//        System.out.println(service.updateStock(itemCode, qty));
        System.out.println(service.getItemCount(itemCode));
    }
}