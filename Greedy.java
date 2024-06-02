package TPE;

import java.util.Iterator;
import java.util.List;

public class Greedy {

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
                return null;
            }
        }
        return sol;        
    }


    private Procesador esFactible(Tarea t, Solucion s, int tiempoNoRefrig){
        
        Iterator<Procesador> procesado = s.getProcesadores();
        Procesador pmejor = procesadores.get(0);
        while (procesado.hasNext()) {
            Procesador p = procesado.next();
            if(p.getTiempoTotal()<pmejor.getTiempoTotal()&&cumpleRestriccion(t,p,tiempoNoRefrig)){
                pmejor=p;
            }
        }
        return pmejor;
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
