package dvm.network.message;

import java.util.Comparator;

/**
 * PFR: 음료 판매 응답 메세지
 */
public class SaleResponseMessage extends Message implements Comparable<SaleResponseMessage> {
    private final String itemCode;
    private final String responseDstId;
    private final int dstX;
    private final int dstY;

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
    public SaleResponseMessage(String dstId, String itemCode) {
        super(dstId, messageType.toString());
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
                getItemCode() + "_" + getResponseDstId() + "_" + getDstX() + "_" + getDstY();
    }


    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * 응답값 sort 위해 compareTo override
     */
    @Override
    public int compareTo(SaleResponseMessage o) {
        int currentX = getCurrentX();
        int currentY = getCurrentX();

        int thisDifferX = currentX - this.getDstX();
        int thisDifferY = currentY - this.getDstY();
        double thisDistance = Math.sqrt(thisDifferX * thisDifferX + thisDifferY * thisDifferY);

        int otherDifferX = currentX - o.getDstX();
        int otherDifferY = currentY - o.getDstY();
        double otherDistance = Math.sqrt(otherDifferX * otherDifferX + otherDifferY * otherDifferY);

        if (thisDistance == otherDistance) {
            if ((this.getSrcId().compareTo(o.getSrcId())) == 1) {
                return 1;
            }
        } else if (thisDistance > otherDistance) {
            return 1;
        }
        return -1;
    }
}
