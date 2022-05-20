package dvm.domain;

public class PrepaymentInfo {

    private final String itemCode;
    private final int quantity;
    private final boolean isValid;

    public PrepaymentInfo(String itemCode, int quantity, boolean isValid) {
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.isValid = isValid;
    }

    public boolean isValid() {
        return isValid;
    }

    public String getItemCode() {
        return itemCode;
    }

    public int getQuantity() {
        return quantity;
    }
}