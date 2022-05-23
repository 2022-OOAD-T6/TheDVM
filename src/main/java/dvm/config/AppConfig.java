package dvm.config;

import dvm.controller.Controller;
import dvm.service.NetworkService;
import dvm.partners.CardCompany;
import dvm.repository.ItemRepository;
import dvm.repository.PrepaymentRepository;
import dvm.service.CardService;
import dvm.service.ItemService;
import dvm.service.PrepaymentService;

import java.io.FileReader;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

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
            controller = new Controller(networkService(), itemService(), prepaymentService(), cardService());
        }
        return controller;
    }

    private static ItemRepository itemRepository() {
        if (itemRepository == null) {
            try{
                // resoureces에 properities/?.properties 파일들 읽어서 세팅 -> 매번 빌드 안하기 위함
                Properties p = new Properties();
                p.load(new FileReader("src/main/resources/properties/stock.properties"));
                ConcurrentHashMap<String, Integer> stock = new ConcurrentHashMap<>();
                for (Object o : p.keySet()) {
                    String key = (String) o;
                    stock.put((String)key, Integer.parseInt(p.getProperty(key)));
                }
                itemRepository = new ItemRepository(stock);
            }catch (Exception e) {
                itemRepository = new ItemRepository();
            }
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
