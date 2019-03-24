import java.util.*;

public class nodo {
    public String nombre;
    public boolean aceptado;
    public ArrayList<arco> arcos;
    public Set<String> cerradura;

    public nodo(String nombre){
        this.nombre = nombre;
        this.aceptado = false;
        arcos = new ArrayList<arco>();
        cerradura = new HashSet<String>();
        cerradura.add(nombre);
    }







}
