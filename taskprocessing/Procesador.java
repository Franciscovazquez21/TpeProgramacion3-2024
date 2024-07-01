package TPE.taskprocessing;

import java.util.LinkedList;
import java.util.List;

public class Procesador {

    private String id;
    private String cod;
    private boolean refrigerado;
    private int anio;
    private List<Tarea> tareasAsignadas;
    private int tiempoTotal;
    private int cantCriticas;
    private int maxCriticas;

    public Procesador(String id, String cod, boolean refrigerado, int anio) {
        
        this.id = id;
        this.cod = cod;
        this.refrigerado = refrigerado;
        this.anio = anio;
        this.tareasAsignadas = new LinkedList<>();
        this.tiempoTotal = 0;
        this.cantCriticas = 0;
        this.maxCriticas=2;
    }

    //limite de tiempo y cant max tareas criticas que pueda procesar
    public boolean cumpleRestriccion (int limiteTprocNoRefrig, Tarea t){
        if(this.getTareasCriticas()<maxCriticas){
            return !this.exedeTiempoEjec(limiteTprocNoRefrig, t);
        }
        return false;
    }

    //funciones de asignacion y designacion de tareas
    public void asignarTarea(Tarea t) {
        if (t.isCritica()) {
            cantCriticas++;//actualiza tareas criticas asignadas
        }
        tiempoTotal += t.getTiempo();
        tareasAsignadas.add(t);
    }

    public void removerTarea(Tarea t) {
        if (t.isCritica()) {
            cantCriticas--;
        }
        tiempoTotal -= t.getTiempo();
        tareasAsignadas.remove(t);
    }

    //control para equipos no refrigerados, tiempoX (limite de carga por procesador)
    public boolean exedeTiempoEjec(int tiempoX, Tarea t) {
        if (!this.isEsRefrigerado()) {
            return tiempoX < this.getTiempoTotal()+t.getTiempo();
        }
        return false;
    }

    //geters
    public int getTiempoTotal() {
        return tiempoTotal;
    }

    public int getTareasCriticas() {
        return cantCriticas;
    }

    public int getMaxCriticas(){
        return maxCriticas;
    }

    public String getidProcesador() {
        return id;
    }

    public String getCodProcesador() {
        return cod;
    }

    public boolean isEsRefrigerado() {
        return refrigerado;
    }

    public int getAnio() {
        return anio;
    }

    //copia en profundidad
    public Procesador getCopia() {
        List<Tarea> aux = new LinkedList<>();
        for (Tarea tarea : tareasAsignadas) {
            Tarea t = tarea.getCopia();
            aux.add(t);
        }
        Procesador copia = new Procesador(this.id, this.cod, this.refrigerado, this.anio);
        copia.tiempoTotal = this.tiempoTotal;
        copia.cantCriticas = this.cantCriticas;
        copia.tareasAsignadas = aux;

        return copia;
    }

    public String toString() {
        String result = id + '\'' +
                ", refrigerado=" + refrigerado +
                ", anio=" + anio + "\n" +
                ", tiempoTotal=" + tiempoTotal +
                ", cantCriticas=" + cantCriticas +
                ", tareasAsignadas:\n" +
                "-----------------------------------------------------------------------------------\n";
        for (Tarea tarea : tareasAsignadas) {
            result += "|tarea: " + tarea + "\n";
            result += "-----------------------------------------------------------------------------------|\n";
        }
        return result;
    }

}
