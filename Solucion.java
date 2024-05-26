package TPE;

    import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

    public class Solucion {
        
        private List<Procesador> procesadores;
        private int tiempoMaxEjec;
        private int cantEstados;

        public Solucion(List<Procesador>procesadores, int tiempoMaxEjec){
            this.procesadores=procesadores;
            this.tiempoMaxEjec=tiempoMaxEjec;
            this.cantEstados=0;
        }

        public Solucion(int tiempoMaxEjec){
            this.tiempoMaxEjec=tiempoMaxEjec;
            this.procesadores=new ArrayList<>();
        }

        public void removeAll(){
            procesadores.clear();
        }

        public void addAll(List<Procesador>nuevaListaProc){
            procesadores.addAll(nuevaListaProc);
        }

        //retorna el valor del procesador que mas tiempo marco de procesamiento(mejorar)
        public int getTiempoMaxEjec(){
            for (Procesador procesador : procesadores) {
                if(procesador.getTiempoTotal()>tiempoMaxEjec){
                    tiempoMaxEjec=procesador.getTiempoTotal();
                
            }
        }
            return tiempoMaxEjec;
    }


        public int getTiempo(){
            return tiempoMaxEjec;
        }
        
        //cantidad de estados generados
        public int cantEstados(){
            return cantEstados;
        }

        public void setCantEstados(int estado){
            this.cantEstados=estado;
        }
        //actualizar estado
        public void addEstado(){
            cantEstados++;
        }
        //actualizar estado
        public void removeEstado(){
            cantEstados--;
        }

        public Iterator<Procesador> getProcesadores(){
            return this.procesadores.iterator();
        }

        public List<Procesador>getListProcesadores(){
            List<Procesador>aux=new ArrayList<>();
                for (Procesador procesador : procesadores) {
                    Procesador p= procesador.getCopia();
                    aux.add(p);
                }
                return aux;
        }

        public Solucion getCopia(){
            List<Procesador>aux=new ArrayList<>();
                for (Procesador procesador : procesadores) {
                    Procesador p= procesador.getCopia();
                    aux.add(p);
                }
            Solucion copia = new Solucion(aux, this.getTiempoMaxEjec());
            copia.cantEstados=this.cantEstados;
            
            return copia;

        }
        

        public void setTiempo(int tiempo){
            this.tiempoMaxEjec=tiempo;
        }


        public String toString(){
            String result="Solucion:\nTiempo maximo ejecucion:"+tiempoMaxEjec+" | Cantidad estados: "+cantEstados+"\n";
                for (Procesador procesador : procesadores) {
                    result+="\ncodigo Procesador"+procesador.toString();
                }
                return result;
        }











    }
