package dvm.config;

import dvm.controller.Controller;
import dvm.gui.AdminPanel;
import dvm.network.MessageFactory;
import dvm.network.Sender;
import dvm.service.NetworkService;
import dvm.partners.CardCompany;
import dvm.repository.ItemRepository;
import dvm.repository.PrepaymentRepository;
import dvm.service.CardService;
import dvm.service.ItemService;
import dvm.service.PrepaymentService;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class AppConfig {

    private static Controller controller;
    private static ItemService itemService;
    private static PrepaymentService prepaymentService;
    private static CardService cardService;
    private static NetworkService networkService;

    public static Controller controller() {
        if (controller == null) {
            controller = new Controller(networkService(), itemService(), prepaymentService(), cardService());
        }
        return controller;
    }

    public static ItemService itemService() {
        if (itemService == null) {
            itemService = new ItemService(ItemRepository.getInstance());
        }
        return itemService;
    }

    public static PrepaymentService prepaymentService() {
        if (prepaymentService == null) {
            prepaymentService = new PrepaymentService(PrepaymentRepository.getInstance());
        }
        return prepaymentService;
    }


    public static CardService cardService() {
        if (cardService == null) {
            cardService = new CardService(CardCompany.getInstance());
        }
        return cardService;
    }

    public static NetworkService networkService() {
        if (networkService == null) {
            try (BufferedReader reader = new BufferedReader
                    (new InputStreamReader(new FileInputStream("src/main/resources/properties/network.properties"), StandardCharsets.UTF_8))) {
                // resources에 properties/?.properties 파일들 읽어서 세팅 -> 매번 빌드 안하기 위함
                Properties p = new Properties();
                p.load(reader);
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
