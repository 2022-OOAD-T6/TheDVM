package dvm.domain;

/**
 * Controller 응답용 data 클래스
 */
public class Response<T> {

    /**
     * 요청 성공 여부
     */
    private boolean isSuccess;

    private ResponseType message;

    /**
     * 결과 객체 리턴해야할 때 리턴
     */
    private T result;

    public Response(boolean isSuccess, ResponseType message, T result) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.result = result;
    }

    public Response(boolean isSuccess, ResponseType message) {
        this.isSuccess = isSuccess;
        this.message = message;
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

    public ResponseType getMessage() {
        return message;
    }

    public void setMessage(ResponseType message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}