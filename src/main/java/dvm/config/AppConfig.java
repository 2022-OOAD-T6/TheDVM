package dvm.config;

import dvm.controller.Controller;
import dvm.network.NetworkService;
import dvm.partners.CardCompany;
import dvm.repository.ItemRepository;
import dvm.repository.PrepaymentRepository;
import dvm.service.CardService;
import dvm.service.ItemService;
import dvm.service.PrepaymentService;

public class AppConfig {

    private static Controller controller;
    private static ItemRepository itemRepository;
    private static PrepaymentRepository prepaymentRepository;
    private static ItemService itemService;
    private static PrepaymentService prepaymentService;
    private static CardCompany cardCompany;
    private static CardService cardService;
    private static NetworkService networkService;


    public static Controller controller() {
        if (controller == null) {
            return new Controller(networkService(), itemService(), prepaymentService(), cardService());
        }
        return controller;
    }

    private static ItemRepository itemRepository() {
        if (itemRepository == null) {
            return new ItemRepository();
        }
        return itemRepository;
    }

    private static PrepaymentRepository prepaymentRepository() {
        if (prepaymentRepository == null) {
            return new PrepaymentRepository();
        }
        return prepaymentRepository;
    }

    private static ItemService itemService() {
        if (itemService == null) {
            return new ItemService(itemRepository());
        }
        return itemService;
    }

    private static PrepaymentService prepaymentService() {
        if (prepaymentService == null) {
            return new PrepaymentService(prepaymentRepository());
        }
        return prepaymentService;
    }

    private static CardCompany cardCompany() {
        if (cardCompany == null) {
            return new CardCompany();
        }
        return cardCompany;
    }

    private static CardService cardService() {
        if (cardService == null) {
            return new CardService(cardCompany());
        }
        return cardService;
    }

    private static NetworkService networkService() {
        if (networkService == null) {
            return new NetworkService("Team6", 50, 30, itemService(), prepaymentService());
        }
        return networkService;
    }

}
