package dvm.network.message;

/**
 * PFR: 선결제 확인 메세지
 */
public class PrepaymentInfoMessage extends Message{
    private final String itemCode;
    private final int quantity;
    private final String verificationCode;

    private static final MessageType messageType = MessageType.PREPAYMENT_INFO;

    /**
     * 외부 자판기 -> 현 자판기
     */
    public PrepaymentInfoMessage(String srcId, String dstId, String type, String itemCode, int quantity, String verificationCode) {
        super(srcId, dstId, type);
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.verificationCode = verificationCode;
    }

    /**
     * 현 자판기 -> 외부 자판기
     */
    public PrepaymentInfoMessage(String dstId, String itemCode, int quantity, String verificationCode){
        super(dstId, messageType.toString());
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.verificationCode = verificationCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    @Override
    public String toString() {
        return getSrcId() + "_" + getDstId() + "_" + messageType + "_" +
                getItemCode() + "_" + getQuantity() + "_" + getVerificationCode();
    }


    @Override
    public MessageType getMessageType() {
        return messageType;
    }
}
