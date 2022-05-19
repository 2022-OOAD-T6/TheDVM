package dvm;

import dvm.network.NetworkService;
import dvm.network.Receiver;
import dvm.network.Sender;
import dvm.network.message.Message;
import dvm.service.ItemService;
import dvm.service.PrepaymentService;

import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        if (args.length < 1) {
            System.out.println("팀 아이디를 입력해주세요 예: Team1");
            return;
        }

        String currentId = args[0];

        ItemService itemService = new ItemService();
        PrepaymentService prepaymentService = new PrepaymentService();
        NetworkService networkService = new NetworkService(currentId, 5, 10, itemService, prepaymentService);

        String[] teamIds = {"Team1", "Team2", "Team3", "Team4", "Team5", "Team6"};
        Random rand = new Random();

        while(true){
            System.out.println("Sleep for 2000ms...");
            Thread.sleep(2000);
            int index = rand.nextInt(6);
            networkService.sendPrepaymentInfoMessage(teamIds[index], "05", 5, "aabbded");
        }
    }
}