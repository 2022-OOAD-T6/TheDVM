package dvm.network.message;

/**
 * PFR: 음료 판매 응답 메세지
 */
public class SaleResponseMessage extends Message{
    private String itemCode;
    private String responseDstId;
    private int dstX;
    private int dstY;

    private static final MessageType messageType = MessageType.SALE_RESPONSE;


    /**
     * 외부 자판기 -> 현 자판기
     */
    public SaleResponseMessage(String srcId, String dstId, String type, String itemCode, String responseDstId, int dstX, int dstY) {
        super(srcId, dstId, type);
        this.itemCode = itemCode;
        this.responseDstId = responseDstId;
        this.dstX = dstX;
        this.dstY = dstY;
    }

    /**
     * 현 자판기 -> 외부 자판기
     */
    public SaleResponseMessage(String dstId, String itemCode){
        super(dstId,messageType.toString());
        this.itemCode = itemCode;
        this.responseDstId = getCurrentId();
        this.dstX = getCurrentX();
        this.dstY = getCurrentY();
    }

    public String getItemCode() {
        return itemCode;
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
                getItemCode() + "_"+getResponseDstId()+"_"+getDstX()+"_"+getDstY();
    }


    @Override
    public MessageType getMessageType() {
        return messageType;
    }
}
