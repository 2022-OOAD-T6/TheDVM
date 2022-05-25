package dvm.network;

public enum MessageType {
    NONE,
    STOCK_REQUEST,
    STOCK_RESPONSE,
    PREPAYMENT_CHECK,
    SALE_REQUEST,
    SALE_RESPONSE;

    // @Override
    // public String toString() {
    //     switch (this) {
    //         case STOCK_REQUEST:
    //             return "0";
    //         case STOCK_RESPONSE:
    //             return "1";
    //         case PREPAYMENT_CHECK:
    //             return "2";
    //         case SALE_REQUEST:
    //             return "3";
    //         case SALE_RESPONSE:
    //             return "4";
    //         case NONE:
    //             return "5";
    //     }
    //     return super.toString();
    // }

    public String getTypeName() {
        switch (this) {
            case STOCK_REQUEST:
                return "StockCheckRequest";
            case STOCK_RESPONSE:
                return "StockCheckResponse";
            case PREPAYMENT_CHECK:
                return "PrepaymentCheck";
            case SALE_REQUEST:
                return "SalesCheckRequest";
            case SALE_RESPONSE:
                return "SalesCheckResponse";
            default:
                return "NONE";
        }
    }

    public static MessageType json2MessageType(String type) {
        switch (type) {
            case "StockCheckRequest":
                return STOCK_REQUEST;
            case "StockCheckResponse":
                return STOCK_RESPONSE;
            case "PrepaymentCheck":
                return PREPAYMENT_CHECK;
            case "SalesCheckRequest":
                return SALE_REQUEST;
            case "SalesCheckResponse":
                return SALE_RESPONSE;
            default:
                return NONE;
        }
    }
}
