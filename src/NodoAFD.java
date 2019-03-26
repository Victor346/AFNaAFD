import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class NodoAFD {
    public String nombre;
    public boolean aceptado;
    public ArrayList<arco> arcos;
    public Set<String> cerradura;
    public Set<String> nodosOriginales;

    public NodoAFD(String nombre) {
        this.nombre = nombre;
        this.aceptado = false;
        arcos = new ArrayList<arco>();
        cerradura = new HashSet<String>();
        nodosOriginales = new HashSet<>();
        cerradura.add(nombre);
    }
    public NodoAFD(String nombre,Set<String> nodosOriginales) {
        this.nombre = nombre;
        this.aceptado = false;
        arcos = new ArrayList<arco>();
        cerradura = new HashSet<String>();
        this.nodosOriginales = new HashSet<>(nodosOriginales);
        cerradura.add(nombre);
    }
}
