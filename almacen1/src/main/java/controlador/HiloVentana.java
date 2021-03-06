
package controlador;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class HiloVentana implements Runnable{

    private String titulo;
    private String ruta_fxml;
    private String ruta_icono;
    
    public HiloVentana(String titulo){
        super();
        this.titulo = titulo;
    }

    public void setRutaFXML(String ruta_fxml) {
        this.ruta_fxml = ruta_fxml;
    }

    public void setRuta_icono(String ruta_icono) {
        this.ruta_icono = ruta_icono;
    }

    private void crearGrafica() {
        try {
            Parent graficos = FXMLLoader.load(getClass().getResource(this.ruta_fxml));
            Scene panel = new Scene(graficos);
            Stage ventana = new Stage();
            ventana.setScene(panel);
            ventana.setResizable(false);
            ventana.getIcons().add(new Image(this.ruta_icono)); 
            ventana.setTitle(this.titulo);
            ventana.show();
        } catch (IOException ex) {
            Logger.getLogger(HiloVentana.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
       crearGrafica();
    }

}
