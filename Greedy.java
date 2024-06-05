package TPE;

import java.util.Iterator;
import java.util.List;

public class Greedy {
    //la lista se recibe ordenada de mayor a menor tiempo.
    private List<Procesador>procesadores;
    private static int candidatosConsiderados;

    public Greedy(List<Procesador>procesadores){
        this.procesadores=procesadores;
        candidatosConsiderados=0;
    }

    
    public Solucion greedy(List<Tarea>candidatos, int tiempoNoRefrig){
        
        Solucion sol= new Solucion(procesadores);
        while(!candidatos.isEmpty()){
            Tarea t = seleccionar(candidatos);
            Procesador p= esFactible(t,sol,tiempoNoRefrig);
            
            if(p!=null){
                p.asignarTarea(t);
                candidatos.remove(t);
            }else{
                return null;//no se puede asignar tarea a procesador(no hay solucion)
            }
        }
        sol.setTiempo(sol.getTiempoMaxEjec());
        return sol;        
    }

    //devuelve el procesador con menor carga segun restricciones, si no encuentra retorna null
    private Procesador esFactible(Tarea t, Solucion s, int tiempoNoRefrig){
        Iterator<Procesador> it = s.getProcesadores();
        Procesador aux=null;
        while (it.hasNext()) {
            Procesador p = it.next();
            //si cumple restricciones y (aux es null o p tiene menos carga que aux)
            if(cumpleRestriccion(t,p,tiempoNoRefrig)&&(aux==null||p.getTiempoTotal()<aux.getTiempoTotal())){
                aux=p;
            }
        }return aux;
    }

    public boolean cumpleRestriccion(Tarea t , Procesador p, int tiempoNoRefrig){
        return (p.getTareasCriticas()<2 &&!p.exedeTiempoEjec(tiempoNoRefrig, t));
    }
    
    public Tarea seleccionar(List<Tarea>candidatos){
        return candidatos.getFirst();
    }

    public static int getCandidatosConsiderados(){
        return candidatosConsiderados;
    }
    
}
