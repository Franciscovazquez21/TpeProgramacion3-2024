package TPE.utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import TPE.taskprocessing.Procesador;
import TPE.taskprocessing.Tarea;


public class CSVReader {

	public CSVReader() {
	}
	
	public ArrayList<Tarea> readTasks(String taskPath) {
		
		// Obtengo una lista con las lineas del archivo
		// lines.get(0) tiene la primer linea del archivo
		// lines.get(1) tiene la segunda linea del archivo... y así
		ArrayList<String[]> lines = this.readContent(taskPath);
		//agrego lista vinculada para las tareas que sean leidas mejor performance que ArrayList.
		ArrayList<Tarea> listaTareas=new ArrayList<>();
		//Map<String,Tarea> mapTareas=new HashMap<>();
		
		for (String[] line: lines) {
			// Cada linea es un arreglo de Strings, donde cada posicion guarda un elemento
			String id = line[0].trim();
			String nombre = line[1].trim();
			Integer tiempo = Integer.parseInt(line[2].trim());
			Boolean critica = Boolean.parseBoolean(line[3].trim());
			Integer prioridad = Integer.parseInt(line[4].trim());
			// Aca instanciar lo que necesiten en base a los datos leidos

			//habria que instanciar un objeto tarea
			Tarea t = new Tarea(id, nombre, tiempo, critica, prioridad);
			listaTareas.add(t);
			//listaTareas.put(id, t);
		}
		 //se devuelve tareas ordenadas por prioridad
        Collections.sort(listaTareas);
		
		return listaTareas;
		
	}
	
public List<Procesador> readProcessors(String processorPath) {
		
		// Obtengo una lista con las lineas del archivo
		// lines.get(0) tiene la primer linea del archivo
		// lines.get(1) tiene la segunda linea del archivo... y así
		ArrayList<String[]> lines = this.readContent(processorPath);
		List<Procesador> listaProcesadores=new LinkedList<>();
		
		for (String[] line: lines) {
			// Cada linea es un arreglo de Strings, donde cada posicion guarda un elemento
			String id = line[0].trim();
			String codigo = line[1].trim();
			Boolean refrigerado = Boolean.parseBoolean(line[2].trim());
			Integer anio = Integer.parseInt(line[3].trim());
			// Aca instanciar lo que necesiten en base a los datos leidos
			Procesador p = new Procesador(id, codigo, refrigerado, anio);
			listaProcesadores.add(p);
		}
		return listaProcesadores;
	}

	private ArrayList<String[]> readContent(String path) {
		ArrayList<String[]> lines = new ArrayList<String[]>();

		File file = new File(path);
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				line = line.trim();
				lines.add(line.split(";"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (bufferedReader != null)
				try {
					bufferedReader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		}
		
		return lines;
	}
	
}
