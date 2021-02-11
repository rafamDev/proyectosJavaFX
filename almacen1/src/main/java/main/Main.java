/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import controlador.ControladorLogin;
import controlador.ControladorMercancia;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vista.LayoutPane;

/**
 *
 * @author rafam
 */
public class Main extends Application{
   
    public void start(Stage ventana) throws Exception {
      LayoutPane layoutPane = new LayoutPane();
      layoutPane.cargarPantalla("almacen",ControladorMercancia.class.getResource("/estilos/controladorMercancia.fxml"));
      layoutPane.cargarPantalla("login",ControladorLogin.class.getResource("/estilos/controladorLogin.fxml"));
      layoutPane.mostrarComoPantallaActual("login");
      Scene marco = new Scene(layoutPane);
      marco.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
      marco.getStylesheets().add(Main.class.getResource("/estilos/estilos.css").toExternalForm());
      ventana.getIcons().add(new Image("/img/icono.png")); 
      ventana.setScene(marco);
      ventana.initStyle(StageStyle.TRANSPARENT);
      ventana.show();
    }
   
    public static void main(String[] args) {
        launch();
    }

}

