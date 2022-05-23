package dvm.config;

import dvm.controller.Controller;
import dvm.service.NetworkService;
import dvm.partners.CardCompany;
import dvm.repository.ItemRepository;
import dvm.repository.PrepaymentRepository;
import dvm.service.CardService;
import dvm.service.ItemService;
import dvm.service.PrepaymentService;

import java.util.logging.Logger;

public class AppConfig {

    private static Controller controller;
    private static ItemRepository itemRepository;
    private static PrepaymentRepository prepaymentRepository;
    private static ItemService itemService;
    private static PrepaymentService prepaymentService;
    private static CardCompany cardCompany;
    private static CardService cardService;
    private static NetworkService networkService;

    static Logger logger = Logger.getGlobal();

    public static Controller controller() {
        if (controller == null) {
            controller = new Controller(networkService(), itemService(), prepaymentService(), cardService());
        }
        return controller;
    }

    private static ItemRepository itemRepository() {
        if (itemRepository == null) {
            itemRepository = new ItemRepository();
        }
        return itemRepository;
    }

    private static PrepaymentRepository prepaymentRepository() {
        if (prepaymentRepository == null) {
            prepaymentRepository = new PrepaymentRepository();
        }
        return prepaymentRepository;
    }

    private static ItemService itemService() {
        if (itemService == null) {
            itemService = new ItemService(itemRepository());
        }
        return itemService;
    }

    private static PrepaymentService prepaymentService() {
        if (prepaymentService == null) {
            prepaymentService = new PrepaymentService(prepaymentRepository());
        }
        return prepaymentService;
    }

    private static CardCompany cardCompany() {
        if (cardCompany == null) {
            cardCompany = new CardCompany();
        }
        return cardCompany;
    }

    private static CardService cardService() {
        if (cardService == null) {
            cardService = new CardService(cardCompany());
        }
        return cardService;
    }

    private static NetworkService networkService() {
        if (networkService == null) {
            networkService = new NetworkService("Team6", 50, 30, itemService(), prepaymentService());
        }
        return networkService;
    }

}
