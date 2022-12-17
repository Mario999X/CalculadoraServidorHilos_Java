package models.mensajes;

public class Response<T> {
    private T content;
    private TypeResponse responseType;

    // Constructor rellenamos en cliente/gestor
    public Response(T content, TypeResponse responseType) {
        this.content = content;
        this.responseType = responseType;
    }

    // Constructor vacio necesario para el funcionamiento de Jackson
    public Response() {
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public TypeResponse getResponseType() {
        return responseType;
    }

    public void setResponseType(TypeResponse responseType) {
        this.responseType = responseType;
    }

    @Override
    public String toString() {
        return "Response{" +
                "content=" + content +
                ", responseType=" + responseType +
                '}';
    }
}
