package modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ArchivoJSON {

private HashMap<Integer, Oferta> listaDeOfertas;
private ArrayList<String> fechaActual;

public ArchivoJSON() {
this.listaDeOfertas=new HashMap<>();
fechaActual =  new ArrayList<String>();
}

public void generarJSON(String archivo) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String json = gson.toJson(this);
    String ruta = System.getProperty("user.dir") + "/src/resources/";
    try {
        FileWriter writer = new FileWriter(ruta + archivo);
        writer.write(json);
        writer.close();
    } catch (Exception e) {
        throw new IllegalArgumentException("ERROR INESPERADO: " + e.getMessage());
    }
}
 
public ArchivoJSON leerJSON(String archivo) {
    Gson gson = new Gson();
    ArchivoJSON ret = null;
    String ruta = System.getProperty("user.dir") + "/src/resources/";
    try {
        BufferedReader br = new BufferedReader(new FileReader(ruta + archivo));
        ret = gson.fromJson(br, ArchivoJSON.class);
    } catch (Exception e) {
        throw new IllegalArgumentException("Error, no se puede cargar el archivo");
    }
    
    return ret;
}

public HashMap<Integer, Oferta> getListaDeOfertas() {
    return this.listaDeOfertas;
}

public void setListaDeOfertas(HashMap<Integer, Oferta> listaDeOfertas) {
    this.listaDeOfertas = listaDeOfertas;
}
public ArrayList<String> getFecha() {
    return this.fechaActual;
}

public void setFecha(ArrayList<String> nuevaFecha) {
    this.fechaActual = nuevaFecha;
}

}
