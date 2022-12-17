package models.mensajes;

public class Request<T> {
    private T content;
    private TypeRequest requestType;

    // Constructor rellenamos en cliente/gestor
    public Request(T content, TypeRequest requestType) {
        this.content = content;
        this.requestType = requestType;
    }

    // Constructor vacio necesario para el funcionamiento de Jackson
    public Request() {
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public TypeRequest getRequestType() {
        return requestType;
    }

    public void setRequestType(TypeRequest requestType) {
        this.requestType = requestType;
    }

    @Override
    public String toString() {
        return "Request{" +
                "content=" + content +
                ", requestType=" + requestType +
                '}';
    }
}
