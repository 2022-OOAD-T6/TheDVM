package dvm.network.message;


/**
 * PFR: 음료 판매 확인 메세지
 */
public class SaleRequestMessage extends Message{
    private final String itemCode;
    private final int quantity;
    private static final MessageType messageType = MessageType.SALE_REQUEST;

    /**
     * 외부 자판기 -> 현 자판기
     */
    public SaleRequestMessage(String srcId, String dstId, String type, String itemCode, int quantity) {
        super(srcId, dstId, type);
        this.itemCode = itemCode;
        this.quantity = quantity;
    }

    /**
     * 현 자판기 -> 외부 자판기
     */
    public SaleRequestMessage(String itemCode, int quantity){
        super("0", messageType.toString());
        this.itemCode = itemCode;
        this.quantity = quantity;
    }

    public String getItemCode() {
        return itemCode;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return getSrcId() + "_" + getDstId() + "_" + messageType + "_" +
                getItemCode() + "_"+getQuantity();
    }


    @Override
    public MessageType getMessageType() {
        return messageType;
    }
}
