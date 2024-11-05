package modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ArchivoJSON {


private HashMap<Integer, Oferta> listaDeOfertas;
public ArchivoJSON() {
this.listaDeOfertas=new HashMap<>();
}

public void generarJSON(String archivo) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String json = gson.toJson(this);
    // Ruta relativa al directorio "caja" dentro del proyecto
    String ruta = System.getProperty("user.dir") + "/src/resources/";

    try {
        FileWriter writer = new FileWriter(ruta + archivo);
        writer.write(json);
        writer.close();
    } catch (Exception e) {
        e.printStackTrace();
        throw new IllegalArgumentException("ERROR INESPERADO: " + e.getMessage());
    }
}

public ArchivoJSON leerJSON(String archivo) {
    Gson gson = new Gson();
    ArchivoJSON ret = null;

    // Ruta relativa al directorio "caja" dentro del proyecto
    String ruta = System.getProperty("user.dir") + "/src/resources/";

    try {
        BufferedReader br = new BufferedReader(new FileReader(ruta + archivo));
        ret = gson.fromJson(br, ArchivoJSON.class);
    } catch (Exception e) {
        throw new IllegalArgumentException("ERROR INESPERADO");
    }
    
    return ret;
}



public HashMap<Integer, Oferta> getListaDeOfertas() {
    return this.listaDeOfertas;
}


public void setListaDeOfertas(HashMap<Integer, Oferta> listaDeOfertas) {
    this.listaDeOfertas = listaDeOfertas;
}

}
