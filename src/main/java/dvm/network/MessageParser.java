package dvm.network;

import dvm.network.message.*;

import static dvm.network.message.MessageType.*;

public class MessageParser {
    public static Message createMessage(String message) throws Exception {
        String[] values = message.split("\\_");
        if(values.length<4){
            throw new Exception("메세지 생성 불가능");
        }

        // TODO: message type 임시 구분
        if (values[2].equals(STOCK_REQUEST.toString())) { // 재고 확인 요청
            return new StockRequestMessage(values[0], values[1], values[2], values[3], Integer.parseInt(values[4]));
        } else if (values[2].equals(STOCK_RESPONSE.toString())) { // 재고 확인 요청
            return new StockResponseMessage(values[0], values[1], values[2], values[3],
                    Integer.parseInt(values[4]), values[5], Integer.parseInt(values[6]),Integer.parseInt(values[7]));
        } else if (values[2].equals(PREPAYMENT_INFO.toString())) { // 선결제 확인
            return new PrepaymentInfoMessage(values[0], values[1], values[2], values[3], Integer.parseInt(values[4]), values[5]);
        } else if (values[2].equals(SALE_REQUEST.toString())) { // 음료 판매 확인
            return new SaleRequestMessage(values[0], values[1], values[2], values[3], Integer.parseInt(values[4]));
        } else if (values[2].equals(SALE_RESPONSE.toString())) { // 음료 판매 응답
            return new SaleResponseMessage(values[0], values[1], values[2], values[3],
                    values[4], Integer.parseInt(values[5]),Integer.parseInt(values[6]));
        }
        System.out.println("메세지 생성 불가능");
        throw new Exception("메세지 생성 불가능");
    }

}
