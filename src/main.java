
import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class main{
    public static void main(String[] args) {
        int Q, E, Q0, QF;
        ArrayList<nodo> nodos = new ArrayList<nodo>();
        ArrayList<nodo> tablaT = new ArrayList<>();
        ArrayList<arco> arcos = new ArrayList<arco>();
        ArrayList<Character> chars = new ArrayList<Character>();
        ArrayList<NodoAFD> AFD = new ArrayList<>();
        int etapa = 0;
        String linTemp;
        int numLetritas = 0;
        int contador = 0;


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
                        tablaT.add(new nodo(linTemp));

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

                        String tabla[] = linTemp.split(";");
                        for(int j=0; j<chars.size(); j++){
                            String tabla2[] = tabla[j].split(",");
                            for(int k=0; k<tabla2.length; k++){
                                nodos.get(contador).arcos.add(new arco(nodos.get(contador).nombre, tabla2[k], chars.get(j)));
                            }
                        }
                        contador++;

                }

            }
        }catch (FileNotFoundException e){
            System.out.println("Archivo no encontrado");
        }
        //System.out.println(nodos.get(0).arcos.get(3).nodoFinal);

        //System.out.println(nodos.get(0).cerradura);
        //Crear cerraduras lambda
        for(nodo element : nodos){
            System.out.println("NuevoElemnto");
            for(arco arc : element.arcos){
                System.out.println(arc.nodoInicial+" "+arc.nodoFinal+" "+arc.letrita);
                if(arc.letrita == 'π'){
                    if(!arc.nodoFinal.equals("0")){
                        element.cerradura.add(arc.nodoFinal);
                    }
                }
            }
        }

        //Imprime y la crea No borrar No es debug
        System.out.println("Cerraduras lambda");
        for(int x=0;x<nodos.size();x++) {
            System.out.println(encontrarCerradura(x, 100, nodos));
        }

        //Copiar Cerraduras
        for(int x=0;x<nodos.size();x++) {
            tablaT.get(x).cerradura = nodos.get(x).cerradura;
        }
        //

        //Aqui se crea la tabla t

        int contadorDos=0;
        int contadorTres=0;
        for(int i = 0; i < nodos.size(); i++){
            for(Character car: chars) {
                Set<String> nuevosArcos = new HashSet();
            for(String elementoCerradura: nodos.get(i).cerradura) {
                contadorDos=0;
                for(int j = 0; j < nodos.size(); j++){
                    if (nodos.get(j).nombre.equals(elementoCerradura)) {
                        contadorDos = j;
                        break;
                    }
                }




                    if (car != 'π'){
                        for (arco ark : nodos.get(contadorDos).arcos) {
                            if (ark.letrita == car && !ark.nodoFinal.equals("0")) {
                                contadorTres=0;
                                for(int x = 0; x < nodos.size(); x++){
                                    if (nodos.get(x).nombre.equals(ark.nodoFinal)) {
                                        contadorTres = x;
                                        break;
                                    }
                                }

                                nuevosArcos.addAll(nodos.get(contadorTres).cerradura);



                            }
                        }

                }

                }
                if(!nuevosArcos.isEmpty()) {
                    for (String cosa : nuevosArcos) {
                        tablaT.get(i).arcos.add(new arco(nodos.get(i).nombre, cosa, car));
                    }
                } else {
                    if(car!='π') {
                        tablaT.get(i).arcos.add(new arco(nodos.get(i).nombre, "0", car));
                    }
                }

            }
        }




        //Aqui ya hay tabla T
        for(nodo element: tablaT) {
            System.out.println(element.nombre);
            for(arco ark : element.arcos){
                System.out.println(ark.nodoInicial+" "+ark.nodoFinal+" "+ark.letrita);
            }
        }

        ///Eliminar pi de chars
        chars.remove(chars.size()-1);
        //Convertir TablaT a AFD
        AFD.add(new NodoAFD(nodos.get(0).cerradura.toString(), nodos.get(0).cerradura));
        ArrayList<NodoAFD> porHacer = new ArrayList<>();
        porHacer.add(AFD.get(0));
        crearAFD(AFD,porHacer,tablaT,chars);

        for(NodoAFD element: AFD) {
            System.out.println(element.nombre);
            for(arco ark : element.arcos){
                System.out.println(ark.nodoInicial+" "+ark.nodoFinal+" "+ark.letrita);
            }
        }

        //Fin Convertir TablaT a AFD

    }

    public static void crearAFD(ArrayList<NodoAFD> AFD, ArrayList<NodoAFD> porHacer, ArrayList<nodo> tablaT,ArrayList<Character> alfabeto){
        int contador = 0;
        ArrayList<NodoAFD> nuevoHacer = new ArrayList<>();
        for(NodoAFD nodito: porHacer){
            for(Character car: alfabeto){
                Set<String> nuevoNodo = new HashSet<>();
                for(String nodoInicial: nodito.nodosOriginales){

                    //Encontrar equivalnecia en tablaT
                    contador=0;
                    for(int x = 0; x < tablaT.size(); x++){
                        if (tablaT.get(x).nombre.equals(nodoInicial) ){
                            contador = x;
                            break;
                        }
                    }

                    for(arco arcoOriginal:tablaT.get(contador).arcos){
                        if(!arcoOriginal.nodoFinal.equals("0")) {
                            if (arcoOriginal.nodoInicial.equals(nodoInicial) && arcoOriginal.letrita == car) {
                                nuevoNodo.add(arcoOriginal.nodoFinal);
                            }
                        }
                    }





                }
                nodito.arcos.add(new arco(nodito.nombre,nuevoNodo.toString(),car));
                boolean flag = true;
                for(NodoAFD nodosYa: AFD){
                    if(nodosYa.nombre.equals(nuevoNodo.toString())){
                        flag = false;
                    }
                }
                int superTemp=0;
                if(flag){
                    AFD.add(new NodoAFD(nuevoNodo.toString(), nuevoNodo));
                    nuevoHacer.add(AFD.get(AFD.size()-1));

                }


            }
        }

        if(nuevoHacer.size()==0){
            System.out.println("Adios");
            return;
        } else {
            System.out.println("Hola");
            crearAFD(AFD,nuevoHacer,tablaT,alfabeto);
        }

    }

    public static Set<String> encontrarCerradura(int estadoInicial,int tamaAnterior,ArrayList<nodo> nodos) {
        int cerraduraSize = nodos.get(estadoInicial).cerradura.size();
        int nuEstado = 0;
        Set<String> temp = new HashSet<>();

        if (cerraduraSize == tamaAnterior) {
            return nodos.get(estadoInicial).cerradura;
        } else

            for (String element : nodos.get(estadoInicial).cerradura) {
                for (int i = 0; i < nodos.size(); i++) {
                    if (element.equals(nodos.get(i).nombre)) {
                        nuEstado = i;
                        break;
                    }
                }
                temp.addAll(encontrarCerradura(nuEstado,cerraduraSize, nodos));
            }
        nodos.get(estadoInicial).cerradura.addAll(temp);
            return encontrarCerradura(estadoInicial,cerraduraSize, nodos);
    }


}


