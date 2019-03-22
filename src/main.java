
import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class main{
    public static void main(String[] args) {
        int Q, E, Q0, QF;
        ArrayList<nodo> nodos = new ArrayList<nodo>();
        ArrayList<arco> arcos = new ArrayList<arco>();
        ArrayList<Character> chars = new ArrayList<Character>();
        int etapa = 0;
        String linTemp;
        int numLetritas = 0;


        try {
            Scanner sc = new Scanner(new File("src/text.txt"));

            sc.nextLine();


            while(sc.hasNext()){
                linTemp = sc.nextLine();

                System.out.println(linTemp);
                if(etapa == 0){
                    if(linTemp.equals("--Alfabeto--")){
                        etapa = 1;

                    }else
                    {
                        nodos.add(new nodo(linTemp));

                    }
                } else if (etapa==1){
                    if(linTemp.equals("--Estados finales--")){
                        etapa = 2;

                    }else
                    {
                        numLetritas++;
                        chars.add(linTemp.charAt(0));

                    }
                } else if (etapa==2){
                    if(linTemp.equals("--Tabla--")){
                        etapa = 3;

                    }else
                    {
                        for(nodo element: nodos){
                            if(element.nombre.equals(linTemp)){
                                element.aceptado = true;
                            }
                        }


                    }
                }else{
                    for(int i=0; i<nodos.size(); i++){
                        String tabla[] = linTemp.split(";");
                        for(int j=0; j<chars.size(); j++){
                            String tabla2[] = tabla[j].split(",");
                            for(int k=0; k<tabla2.length; k++){
                                nodos.get(i).arcos.add(new arco(nodos.get(i).nombre, tabla2[k], chars.get(j)));
                            }
                        }
                    }
                }

            }
        }catch (FileNotFoundException e){
            System.out.println("ola");
        }
        System.out.println(nodos.get(0).arcos.get(3).nodoFinal);

        //Crear cerraduras lambda


    }
}
