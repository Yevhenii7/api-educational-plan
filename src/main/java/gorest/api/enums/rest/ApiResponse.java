package gorest.api.enums.rest;

public class ApiResponse<T> {
    private final int statusCode;
    private final T body;

    public ApiResponse(int statusCode, T body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public T getBody() {
        return body;
    }
}
