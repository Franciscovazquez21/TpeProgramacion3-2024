package TPE.algorithms;

import java.util.Iterator;
import java.util.List;

import TPE.taskprocessing.Procesador;
import TPE.taskprocessing.Solucion;
import TPE.taskprocessing.Tarea;

public class Backtraking {
    
    public Solucion backtraking(int limiteTprocNoRefrig, Solucion estadoActual, Solucion solucion,List<Tarea> tareasXasignar) {
        solucion.incrementarEstado();
        if(tareasXasignar.isEmpty()){//no hay mas tareas por asignar,verifica si es solucion
            if(esSolucion(estadoActual,solucion)){
                operarSolucion(estadoActual,solucion);//actualiza solucion
            }
        }else{
            Tarea siguiente= tareasXasignar.get(0);
            Iterator<Procesador> procesadores = estadoActual.getProcesadores();
            
            while (procesadores.hasNext()) {
                Procesador p = procesadores.next();
                if(p.cumpleRestriccion(siguiente, limiteTprocNoRefrig)){
                    asignarTarea(p,siguiente,tareasXasignar);
                    if(!poda(p, siguiente, solucion)){
                        backtraking(limiteTprocNoRefrig, estadoActual, solucion, tareasXasignar);
                    }
                    retirarTarea(p,siguiente,tareasXasignar);
                }
            }
        }
        return solucion;
    }
        
    //compara valores de soluciones
    private boolean esSolucion(Solucion actual, Solucion solucion){
        if(solucion.getTiempo()==-1){//se cumple en el primer estado final
            return true;
        }else{
            return actual.getTiempoMaxEjec()<solucion.getTiempo();
        }        
    }

    //nueva solucion pasa a ser la solucion
    private void operarSolucion(Solucion nuevaSolucion, Solucion solucion){
        solucion.removeAll();
        solucion.addAll(nuevaSolucion.getCopiaProcesadores());
        solucion.setTiempo(nuevaSolucion.getTiempoMaxEjec());
        solucion.setTiempo(nuevaSolucion.getTiempoMaxEjec());
    }

    //asignaciones y designaciones de tareas
    private void asignarTarea(Procesador p, Tarea t,List<Tarea>tareas){
        p.asignarTarea(t);
        tareas.remove(t);
    }

    private void retirarTarea(Procesador p, Tarea t,List<Tarea>tareas){
        p.removerTarea(t);
        tareas.add(t);
    }

    //poda 
    private boolean poda(Procesador p, Tarea t, Solucion s){
        if(s.getTiempo()==-1){//compara mientras no haya ningun estado solucion
            return false;
        }
        return (p.getTiempoTotal()+t.getTiempo())>s.getTiempo();
    }
        
}

