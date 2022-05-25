package dvm.domain;

/**
 * Controller 응답용 data 클래스
 */
public class Response<T> {

    /**
     * 요청 성공 여부
     */
    private boolean isSuccess;

    private ResponseType responseType;

    /**
     * 결과 객체 리턴해야할 때 리턴
     */
    private T result;

    public Response(boolean isSuccess, ResponseType responseType, T result) {
        this.isSuccess = isSuccess;
        this.responseType = responseType;
        this.result = result;
    }

    public Response(boolean isSuccess, ResponseType responseType) {
        this.isSuccess = isSuccess;
        this.responseType = responseType;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public T getResult() {
        return result;
    }
}