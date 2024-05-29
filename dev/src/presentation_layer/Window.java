package presentation_layer;

public abstract class Window {
    protected boolean open;

    public Window(){
        this.open = false;
    }
    public abstract Window run(Controller controller);
    public abstract void close();
    public abstract void open();
}
