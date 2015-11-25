package models;

/**
 * Created by Reist on 16.11.15.
 */
public class Response<T> {

    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}
