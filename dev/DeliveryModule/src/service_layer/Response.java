package service_layer;

public class  Response<T> {
    T object;
    String errorMessage;

    public Response(){
        this.object = null;
        this.errorMessage = null;
    }

    public Response(T object){
        this.object = object;
    }

    public Response(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public boolean isError() {
        return errorMessage != null;
    }
    public String getErrorMessage() {
        if (errorMessage == null)
            throw new RuntimeException("No error message");
        return errorMessage;
    }
    public T getObject() {
        if (object == null)
            throw new RuntimeException("No object");
        return object;
    }

}
