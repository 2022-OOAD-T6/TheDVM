package dvm.config;

import dvm.controller.Controller;
import dvm.network.MessageFactory;
import dvm.network.Sender;
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

    public static ItemRepository itemRepository() {
        if (itemRepository == null) {
            try {
                // resources에 properties/?.properties 파일들 읽어서 세팅 -> 매번 빌드 안하기 위함
                Properties p = new Properties();
                p.load(new FileReader("src/main/resources/properties/stock.properties"));
                ConcurrentHashMap<String, Integer> stock = new ConcurrentHashMap<>();
                for (Object o : p.keySet()) {
                    String key = (String) o;
                    stock.put((String) key, Integer.parseInt(p.getProperty(key)));
                }
                itemRepository = new ItemRepository(stock);
            } catch (Exception e) {
                itemRepository = new ItemRepository();
            }
        }
        return itemRepository;
    }

    public static PrepaymentRepository prepaymentRepository() {
        if (prepaymentRepository == null) {
            prepaymentRepository = new PrepaymentRepository();
        }
        return prepaymentRepository;
    }

    public static ItemService itemService() {
        if (itemService == null) {
            itemService = new ItemService(itemRepository());
        }
        return itemService;
    }

    public static PrepaymentService prepaymentService() {
        if (prepaymentService == null) {
            prepaymentService = new PrepaymentService(prepaymentRepository());
        }
        return prepaymentService;
    }

    public static CardCompany cardCompany() {
        if (cardCompany == null) {
            cardCompany = new CardCompany();
        }
        return cardCompany;
    }

    public static CardService cardService() {
        if (cardService == null) {
            cardService = new CardService(cardCompany());
        }
        return cardService;
    }

    public static NetworkService networkService() {
        if (networkService == null) {
            try {
                // resources에 properties/?.properties 파일들 읽어서 세팅 -> 매번 빌드 안하기 위함
                Properties p = new Properties();
                p.load(new FileReader("src/main/resources/properties/network.properties"));
                MessageFactory.setCurrentId(p.getProperty("current.id"));
                MessageFactory.setCurrentX(Integer.parseInt(p.getProperty("current.x")));
                MessageFactory.setCurrentY(Integer.parseInt(p.getProperty("current.y")));
                Sender.initDvmsNetworkInfo(p.getProperty("other.id").replace(" ", "").split(","),
                        p.getProperty("other.ip").replace(" ", "").split(","));
            } catch (Exception e) {
                e.printStackTrace();
                String currentId = "Team6";
                int currentX = 30;
                int currentY = 50;
                String[] ids = new String[]{"Team1"};
                String[] ips = new String[]{"127.0.0.1"};
                MessageFactory.setCurrentId(currentId);
                MessageFactory.setCurrentX(currentX);
                MessageFactory.setCurrentY(currentY);
                Sender.initDvmsNetworkInfo(ids, ips);
            }
            System.out.println("-----------------현재 네트워크 정보-------------------");
            MessageFactory.printCurrentInfo();
            Sender.printDvmsNetworkInfo();
            System.out.println("--------------------------------------------------");
            networkService = new NetworkService(itemService(), prepaymentService());
        }
        return networkService;
    }

}
