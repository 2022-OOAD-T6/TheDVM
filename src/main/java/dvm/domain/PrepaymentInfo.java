package dvm.domain;

/**
 * 
 */
public class PrepaymentInfo {

    /**
     * Default constructor
     */
    public PrepaymentInfo() {
    }

    /**
     * 
     */
    private boolean isValid;

    /**
     * 
     */
    private String itemCode;

    /**
     * 
     */
    private int quantity;

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