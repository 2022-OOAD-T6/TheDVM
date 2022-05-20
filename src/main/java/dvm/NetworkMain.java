package dvm;

import dvm.network.NetworkService;
import dvm.repository.ItemRepository;
import dvm.repository.PrepaymentRepository;
import dvm.service.ItemService;
import dvm.service.PrepaymentService;

import java.util.Random;

public class NetworkMain {
    public static void main(String[] args) throws InterruptedException {
        if (args.length < 1) {
            System.out.println("팀 아이디를 입력해주세요 예: Team1");
            return;
        }

        String currentId = args[0];

        ItemService itemService=new ItemService(new ItemRepository());
        PrepaymentService prepaymentService = new PrepaymentService(new PrepaymentRepository());
        NetworkService networkService = new NetworkService(currentId, 5, 10, itemService, prepaymentService);

        String[] teamIds = {"Team1", "Team2", "Team3", "Team4", "Team5", "Team6"};
        Random dstRand = new Random();
        Random typeRand = new Random();

        while (true) {
            Thread.sleep(2000);
            int dstIndex = dstRand.nextInt(6);
            int typeIndex = typeRand.nextInt(4);

            switch (typeIndex) {
                case 0:
                    networkService.sendStockRequestMessage("10", 20);
                    break;
                case 1:
                    networkService.sendStockResponseMessage(teamIds[dstIndex], "05", 2);
                    break;
                case 2:
                    networkService.sendPrepaymentInfoMessage(teamIds[dstIndex], "04", 5, "aabbded");
                    break;
                case 3:
                    networkService.sendSaleRequestMessage("12", 24);
                    break;
                case 4:
                    networkService.sendSaleResponseMessage(teamIds[dstIndex], "08");
            }
        }
    }
}
