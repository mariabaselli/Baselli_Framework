package baselli.framework;

public class Start {
    private Consola consola;

    public Start(String pathConfiguracion) {
        this.consola = new Consola(pathConfiguracion);
    }

    public void init(){
        consola.imprimir();
    }
}
