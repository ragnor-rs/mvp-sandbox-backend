package models;

/**
 * Created by Reist on 16.11.15.
 */
public class Response<T> {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
