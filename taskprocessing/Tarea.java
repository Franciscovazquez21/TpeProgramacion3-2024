package TPE.taskprocessing;

public class Tarea implements Comparable<Tarea> {

    private String id;
    private String nombre;
    private Integer tiempo;
    private boolean critica;
    private Integer prioridad;


    public Tarea(String id, String nombre, Integer tiempo, boolean critica, Integer prioridad) {
        this.id = id;
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.critica = critica;
        this.prioridad = prioridad;
    }

    //geters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getTiempo() {
        return tiempo;
    }

    public boolean isCritica() {
        return critica;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public Tarea getCopia(){
        return new Tarea(this.id,this.nombre,this.tiempo,this.critica,this.prioridad);
    }
    
    //implementado para que CSV devuelva tareas ordenadas por prioridad
    @Override
    public int compareTo(Tarea t) {
        return this.getPrioridad().compareTo(t.getPrioridad());
    }

    public String toString(){
        return "Tarea Id:"+this.getId()+", Nombre: "+this.getNombre()+", Prioridad: "+this.getPrioridad()+
                ", Tiempo: "+this.getTiempo()+", Critica: "+this.isCritica();
    }

}
