package dvm.network;

/**
 * 
 */
public class Message {

    /**
     * Default constructor
     */
    public Message() {
    }

    /**
     * 
     */
    private String srcId;

    /**
     * 
     */
    private String dstId;

    /**
     * 
     */
    private String type;

    /**
     * 
     */
    private String description;

    public String getSrcId() {
        return srcId;
    }

    public String getDstId() {
        return dstId;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}