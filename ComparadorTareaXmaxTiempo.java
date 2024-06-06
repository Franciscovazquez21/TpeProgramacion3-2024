package TPE;

import java.util.Comparator;

public class ComparadorTareaXmaxTiempo implements Comparator<Tarea> {

    @Override
    public int compare(Tarea t1, Tarea t2) {
        return t2.getTiempo().compareTo(t1.getTiempo()); 
    }
    
}
