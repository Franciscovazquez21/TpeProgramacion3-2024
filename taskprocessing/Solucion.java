package TPE.taskprocessing;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Solucion {
    
    //contiene la lista de procesadores y los parametros que representan la solucion        
    private List<Procesador> procesadores;
    private int tiempoMaxEjec;
    private int cantEstados_candidatos;

    //constructor 1 (para iniciar estadoActual)
    public Solucion(List<Procesador>procesadores){
        this.procesadores=procesadores;
        this.tiempoMaxEjec=0;
        this.cantEstados_candidatos=0;
    }

    //constructor 2 (para inciar estadoSolucion)
    public Solucion(int tiempoMaxEjec){
        this.tiempoMaxEjec=tiempoMaxEjec;
        this.procesadores=new LinkedList<>();
        this.cantEstados_candidatos=0;
    }   
    
    //compara tiempos maximos de soluciones
    public boolean esSolucion(Solucion s){
        if(s.getTiempo()==-1){
            return true;
        }else{
            return this.getTiempoMaxEjec()<s.getTiempo();
        }     
    }

    //vacia lista de procesadores
    public void removeAll(){
        procesadores.clear();
    }

    //agrega la nueva lista de procesadores
    public void addAll(List<Procesador>nuevaListaProc){
        procesadores.addAll(nuevaListaProc);
    }

    //procesador con mayor tiempo de ejecucion
    public int getTiempoMaxEjec(){
        int tiempo=0;
        for (Procesador procesador : procesadores) {
            if(procesador.getTiempoTotal()>tiempo){
                tiempo=procesador.getTiempoTotal();
            }
        }
        return tiempo;
    }

    //mayor tiempo ejecucion
    public int getTiempo(){
        return tiempoMaxEjec;
    }

    public void setTiempo(int tiempo){
        this.tiempoMaxEjec=tiempo;
    }
    
    //incrementar estado
    public void incrementarEstado(){
        cantEstados_candidatos++;
    }

    //procesadores para iterar
    public Iterator<Procesador> getProcesadores(){
        return this.procesadores.iterator();
    }

    //copia de procesadores
    public List<Procesador>getCopiaProcesadores(){
        List<Procesador>aux=new LinkedList<>();
            for (Procesador procesador : procesadores) {
                Procesador p= procesador.getCopia();
                aux.add(p);
            }
            return aux;
    }

    public String toString(){
        String result="Solucion:\nTiempo maximo ejecucion:"+tiempoMaxEjec+" | Cantidad estados_candidatos: "+cantEstados_candidatos+"\n";
            for (Procesador procesador : procesadores) {
                result+="\ncodigo Procesador"+procesador.toString();
            }
            return result;
    }

}
