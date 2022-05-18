package dvm.network.message;

/**
 *
 */
public abstract class Message {

    // TODO: 현 DVM의 좌표 정보 -> 후에 좌표 정보 주어진 후 구체적으로 수정
    private static final int currentX = 10;
    private static final int currentY = 10;
    private static final String currentId = "Team6";

    private String srcId;
    private String dstId;
    private String type;

    public Message(String srcId, String dstId, String type) {
        this.srcId = srcId;
        this.dstId = dstId;
        this.type = type;
    }

    public Message(String dstId, String type){
        this(currentId, dstId, type);
    }

    public String getSrcId() {
        return srcId;
    }

    public String getDstId() {
        return dstId;
    }

    public String getType() {
        return type;
    }

    public static int getCurrentX() {
        return currentX;
    }

    public static int getCurrentY() {
        return currentY;
    }

    public static String getCurrentId() {
        return currentId;
    }

    public abstract String toString();

    public abstract MessageType getMessageType();
}