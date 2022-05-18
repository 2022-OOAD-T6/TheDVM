package dvm.network.message;

public enum MessageType {
    STOCK_REQUEST,
    STOCK_RESPONSE,
    PREPAYMENT_INFO,
    SALE_REQUEST,
    SALE_RESPONSE;

    @Override
    public String toString() {
        switch(this){
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
        }
        return super.toString();
    }
}
