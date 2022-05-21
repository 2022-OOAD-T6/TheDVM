package dvm.network;

/**
 * Controller 응답용 data 클래스
 */
public class Response<T> {

    /**
     * 요청 성공 여부
     */
    private boolean isSuccess;

    private String message;

    /**
     * 결과 객체 리턴해야할 때 리턴
     */
    private T result;

    public Response(boolean isSuccess, String message, T result) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.result = result;
    }
    public Response(boolean isSuccess){
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}