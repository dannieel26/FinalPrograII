
package com.mycompany.proyectofinal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Pablo
 */
public class Rutas {
    private final String archivoRuta = "ruta_predefinida.txt";
    
    public void obtenerRutaSeleccionada(String ruta){
        try{
            FileWriter writer = new FileWriter(archivoRuta);
            writer.write(ruta);
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public String leerRutaObtenida(){
        String ruta = null;
        try{
            File archivo = new File(archivoRuta);
            if (archivo.exists()){
                BufferedReader reader = new BufferedReader(new FileReader(archivo));
                ruta = reader.readLine();
                reader.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return ruta;
    }
}
