package dvm.network.message;

/**
 *
 */
public abstract class Message {

    // TODO: 현 DVM의 좌표 정보 -> 후에 좌표 정보 주어진 후 구체적으로 수정
    private static int currentX = 10;
    private static int currentY = 10;
    private static String currentId = "Team6";

    private String srcId;
    private String dstId;
    private String type;

    public Message(String srcId, String dstId, String type) {
        this.srcId = srcId;
        this.dstId = dstId;
        this.type = type;
    }

    public Message(String dstId, String type) {
        this(currentId, dstId, type);
    }


    public String getSrcId() {
        return srcId;
    }

    public String getDstId() {
        return dstId;
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

    public void setDstId(String dstId) {
        this.dstId = dstId;
    }

    public static void setCurrentX(int x) {
        Message.currentX = x;
    }

    public static void setCurrentY(int y) {
        Message.currentY = y;
    }

    public static void setCurrentId(String currentId) {
        Message.currentId = currentId;
    }

    public abstract String toString();

    public abstract MessageType getMessageType();
}