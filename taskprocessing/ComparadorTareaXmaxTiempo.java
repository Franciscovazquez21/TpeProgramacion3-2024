package TPE.taskprocessing;

import java.util.Comparator;
//comparador utilizado para estraegia Greedy
public class ComparadorTareaXmaxTiempo implements Comparator<Tarea> {

    @Override
    public int compare(Tarea t1, Tarea t2) {
        return t2.getTiempo().compareTo(t1.getTiempo()); 
    }
    
}
