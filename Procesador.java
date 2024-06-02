package TPE;

import java.util.ArrayList;
import java.util.List;

public class Procesador {

    private String idProcesador;
    private String codProcesador;
    private boolean esRefrigerado;
    private int anio;
    private List<Tarea> tareasAsignadas;
    private int tiempoTotal;
    private int cantCriticas;
    private int maxCriticas;

    public Procesador(String idProcesador, String codProcesador, boolean esRefrigerado, int anio) {
        
        this.idProcesador = idProcesador;
        this.codProcesador = codProcesador;
        this.esRefrigerado = esRefrigerado;
        this.anio = anio;
        this.tareasAsignadas = new ArrayList<>();
        this.tiempoTotal = 0;
        this.cantCriticas = 0;
        this.maxCriticas=2;
    }

    //limite de tiempo y cant max tareas criticas que pueda procesar
    public boolean cumpleRestriccion (Tarea t, int limiteTprocNoRefrig){
        return (!this.exedeTiempoEjec(limiteTprocNoRefrig, t)&&this.getTareasCriticas()<maxCriticas);
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

    //control para equipos no refrigerados, tiempoX (limite de carga por tarea)
    public boolean exedeTiempoEjec(int tiempoX, Tarea t) {
        if (!this.isEsRefrigerado()) {
            return tiempoX < t.getTiempo();
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

    public String getidProcesador() {
        return idProcesador;
    }

    public String getCodProcesador() {
        return codProcesador;
    }

    public boolean isEsRefrigerado() {
        return esRefrigerado;
    }

    public int getAnio() {
        return anio;
    }

    //copia en profundidad
    public Procesador getCopia() {
        List<Tarea> aux = new ArrayList<>();
        for (Tarea tarea : tareasAsignadas) {
            Tarea t = tarea.getCopia();
            aux.add(t);
        }
        Procesador copia = new Procesador(this.idProcesador, this.codProcesador, this.esRefrigerado, this.anio);
        copia.tiempoTotal = this.tiempoTotal;
        copia.cantCriticas = this.cantCriticas;
        copia.tareasAsignadas = aux;

        return copia;
    }

    public String toString() {
        String result = idProcesador + '\'' +
                ", esRefrigerado=" + esRefrigerado +
                ", anio=" + anio + "\n" +
                ", tiempoTotal=" + tiempoTotal +
                ", cantCriticas=" + cantCriticas +
                ", tareasAsignadas:\n" +
                "-----------------------------------------------------------------------------------\n";
        for (Tarea tarea : tareasAsignadas) {
            result += "-----------------------------------------------------------------------------------|\n";
            result += "|tarea: " + tarea + "\n";
            result += "-----------------------------------------------------------------------------------|\n";
        }
        return result;
    }

}
