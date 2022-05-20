package dvm.network.message;

public enum MessageType {
    NONE,
    STOCK_REQUEST,
    STOCK_RESPONSE,
    PREPAYMENT_INFO,
    SALE_REQUEST,
    SALE_RESPONSE;

    @Override
    public String toString() {
        switch (this) {
            case STOCK_REQUEST:
                return "0";
            case STOCK_RESPONSE:
                return "1";
            case PREPAYMENT_INFO:
                return "2";
            case SALE_REQUEST:
                return "3";
            case SALE_RESPONSE:
                return "4";
            case NONE:
                return "5";
        }
        return super.toString();
    }

    public String getTypeName() {
        switch (this) {
            case STOCK_REQUEST:
                return "재고 확인 요청";
            case STOCK_RESPONSE:
                return "재고 확인 응답";
            case PREPAYMENT_INFO:
                return "선결제 확인";
            case SALE_REQUEST:
                return "음료 판매 확인";
            case SALE_RESPONSE:
                return "음료 판매 응답";
            default:
                return "없음";
        }
    }
}
