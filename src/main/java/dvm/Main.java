package dvm;

import dvm.controller.Controller;
import dvm.gui.MainFrame;
import dvm.network.NetworkService;
import dvm.partners.CardCompany;
import dvm.repository.ItemRepository;
import dvm.repository.PrepaymentRepository;
import dvm.service.CardService;
import dvm.service.ItemService;
import dvm.service.PrepaymentService;

public class Main {
    public static void main(String[] args) {
//         System.out.println("Hello world!");
//
//
//         // item testing
//         ItemRepository ir = new ItemRepository();
//         ItemService service=new ItemService(ir);
//
//         String itemCode = "01";
//         int qty = 111;
//         System.out.println(service.isEnough(itemCode, qty));
//         System.out.println(service.getItemPrice(itemCode));
// //        System.out.println(service.updateStock(itemCode, qty));
//         System.out.println(service.getItemCount(itemCode));
        ItemService itemService = new ItemService(new ItemRepository());
        PrepaymentService prepaymentService = new PrepaymentService(new PrepaymentRepository());
        new MainFrame(new Controller(new NetworkService("Team6", 10, 10, itemService, prepaymentService), itemService,
                prepaymentService, new CardService(new CardCompany())));
    }
}