package dvm;

import dvm.service.NetworkService;
import dvm.repository.ItemRepository;
import dvm.repository.PrepaymentRepository;
import dvm.service.ItemService;
import dvm.service.PrepaymentService;

import java.util.Random;

public class NetworkMain {
    public static void main(String[] args) throws InterruptedException {
        String currentId;
        if (args.length < 1) {
            System.out.println("팀 아이디를 입력해주세요 예: Team1. 기본으로 Team6이 id가 됩니다.");
            currentId = "Team6";
        }else{
            currentId = args[0];
        }

        ItemService itemService=new ItemService(new ItemRepository());
        PrepaymentService prepaymentService = new PrepaymentService(new PrepaymentRepository());
        NetworkService networkService = new NetworkService(currentId, 5, 10, itemService, prepaymentService);

        String[] teamIds = {"Team1", "Team2", "Team3", "Team4", "Team5", "Team6"};
        Random dstRand = new Random();
        Random typeRand = new Random();

        while (true) {
            Thread.sleep(5000);
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

        // while(true){
        //     Thread.sleep(1000);
        //     Message message = new Message();
        //     message.setSrcId("Team6");
        //     message.setDstID("Team1");
        //     MessageDescription messageDescription = new MessageDescription();
        //     messageDescription.setItemCode("item");
        //     messageDescription.setItemNum(10);
        //     message.setMsgDescription(messageDescription);
        //     Serializer serializer = new Serializer();
        //     try{
        //         DVMClient client = new DVMClient("localhost", serializer.message2Json(message));
        //         client.run();
        //     }catch(Exception e){
        //     }
        // }
    }
}
