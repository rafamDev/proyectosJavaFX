/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;


import controlador.ControladorConNavegabilidad;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author rafam
 */
public class LayoutPane extends BorderPane{
       
    private Map<String,Node> pantallaDeAplicacion;
    
    public LayoutPane(){
      this.pantallaDeAplicacion = new HashMap<String,Node>();
    }
    
    public void cargarPantalla(String nombrePantalla, URL urlArchivoFxml){
        try {
            FXMLLoader cargadorPantalla = new FXMLLoader(urlArchivoFxml);
            Parent pantalla = cargadorPantalla.load();
            ControladorConNavegabilidad controladorConNavegabilidad = cargadorPantalla.getController();
            controladorConNavegabilidad.setLayout(this);
            this.pantallaDeAplicacion.put(nombrePantalla,pantalla);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void mostrarComoPantallaActual(String nombrePantalla){
       this.setCenter(this.pantallaDeAplicacion.get(nombrePantalla));
    }

}
