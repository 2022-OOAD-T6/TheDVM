package dvm.network.message;

/**
 * PFR: 재고 확인 응답 메세지
 */
public class StockResponseMessage extends Message{
    private String itemCode;
    private int quantity;
    private String responseDstId;

    private static final MessageType messageType = MessageType.STOCK_RESPONSE;


    // TODO: 좌표 형태 일단 X_Y -> _로 구분
    private int dstX;
    private int dstY;

    /**
     * 외부 자판기 -> 현 자판기
     */
    public StockResponseMessage(String srcId, String dstId, String type, String itemCode, int quantity, String responseDstId, int dstX, int dstY) {
        super(srcId, dstId, type);
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.responseDstId = responseDstId;
        this.dstX = dstX;
        this.dstY = dstY;
    }

    /**
     * 현 자판기 -> 외부 자판기
     */
    public StockResponseMessage(String dstId, String itemCode, int quantity){
        super(dstId, "1");
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.responseDstId = getCurrentId();
        this.dstX=getCurrentX();
        this.dstY=getCurrentY();
    }

    public String getItemCode() {
        return itemCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getResponseDstId() {
        return responseDstId;
    }

    public int getDstX() {
        return dstX;
    }

    public int getDstY() {
        return dstY;
    }

    @Override
    public String toString() {
        return getSrcId() + "_" + getDstId() + "_" + messageType + "_" +
                getItemCode() + "_"+getQuantity()+"_"+getResponseDstId()+"_"+getDstX()+"_"+getDstY();
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }
}
