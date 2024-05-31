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

    public Procesador(String idProcesador, String codProcesador, boolean esRefrigerado, int anio) {
        
        this.idProcesador = idProcesador;
        this.codProcesador = codProcesador;
        this.esRefrigerado = esRefrigerado;
        this.anio = anio;
        this.tiempoTotal = 0;
        this.cantCriticas = 0;
        this.tareasAsignadas = new ArrayList<>();
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

    public void asignarTarea(Tarea t) {
        if (t.isCritica()) {
            cantCriticas++;
        }
        tiempoTotal += t.getTiempo();
        tareasAsignadas.add(t);
    }

    public int getTiempoTotal() {
        return tiempoTotal;
    }

    public int getTareasCriticas() {
        return cantCriticas;
    }

    public void removerTarea(Tarea t) {
        if (t.isCritica()) {
            cantCriticas--;
        }
        tiempoTotal -= t.getTiempo();
        tareasAsignadas.remove(t);
    }

    // control para equipos no refrigerados
    public boolean exedeTiempoEjec(int tiempoX, Tarea t) {
        if (!this.isEsRefrigerado()) {
            return tiempoX < t.getTiempo();
        }
        return false;
    }

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
