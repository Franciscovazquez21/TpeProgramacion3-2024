package TPE;

import java.util.ArrayList;
import java.util.List;

public class Procesador {

    private String id_procesador;
    private String codigo_procesador;
    private boolean esta_refrigerado;
    private int anio;
    private List<Tarea>tareasAsignadas;
    private int tiempoTotal;
    private int cantCriticas;

    public Procesador(String id_procesador, String codigo_procesador, boolean esta_refrigerado, int anio) {
       
        this.id_procesador = id_procesador;
        this.codigo_procesador = codigo_procesador;
        this.esta_refrigerado = esta_refrigerado;
        this.anio = anio;
        this.tiempoTotal=0;
        this.cantCriticas=0;
        this.tareasAsignadas=new ArrayList<>();
    }

    public String getId_procesador() {
        return id_procesador;
    }

    public String getCodigo_procesador() {
        return codigo_procesador;
    }

    public boolean isEsta_refrigerado() {
        return esta_refrigerado;
    }

    public int getAnio() {
        return anio;
    }

    public void asignarTarea(Tarea t){
        if(t.isCritica()){
            cantCriticas++;
        }
        tiempoTotal+=t.getTiempo();
        tareasAsignadas.add(t);
    }

    public int getTiempoTotal(){
        return tiempoTotal;
    }

    public int getTareasCriticas(){
        return cantCriticas;
    }

    public void removerTarea(Tarea t){
        if(t.isCritica()){
            cantCriticas--;
        }
        tiempoTotal-=t.getTiempo();
        tareasAsignadas.remove(t);
    }
    //control para equipos no refrigerados
    public boolean exedeTiempoEjec(int tiempoX, Tarea t){
        if(!this.isEsta_refrigerado()){
            return tiempoX<t.getTiempo();
        }return false;
    }

    public Procesador getCopia(){
        List<Tarea>aux=new ArrayList<>();
        for (Tarea tarea : tareasAsignadas) {
            Tarea t= tarea.getCopia();
            aux.add(t);
        }
        Procesador copia = new Procesador(this.id_procesador, this.codigo_procesador, this.esta_refrigerado, this.anio);
        copia.tiempoTotal=this.tiempoTotal;
        copia.cantCriticas=this.cantCriticas;
        copia.tareasAsignadas=aux;

        return copia;
    }

    public String toString() {
        String result="Procesador{" +
                "id_procesador='" + id_procesador + '\'' +
                ", codigo_procesador='" + codigo_procesador + '\'' +
                ", esta_refrigerado=" + esta_refrigerado +
                ", anio=" + anio +"\n"+
                ", tiempoTotal=" + tiempoTotal +
                ", cantCriticas=" + cantCriticas +
                ", tareasAsignadas:\n";

                for(Tarea tarea : tareasAsignadas) {
                    result+="--------------------------------------------------------------------\n";
                    result+="tarea: "+tarea+"\n";
                    result+="--------------------------------------------------------------------\n";
                }
            return result;
                
            
            }

    
    
}
