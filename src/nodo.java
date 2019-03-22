import java.util.ArrayList;

public class nodo {
    public String nombre;
    public boolean aceptado;
    public ArrayList<arco> arcos;

    public nodo(String nombre){
        this.nombre = nombre;
        this.aceptado = false;
        arcos = new ArrayList<arco>();
    }






}
