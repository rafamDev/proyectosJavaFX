/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import controlador.ControladorGrafica;
import controlador.ControladorLogin;
import controlador.ControladorMercancia;
import controlador.ControladorRegistro;
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
      layoutPane.cargarPantalla("registro",ControladorRegistro.class.getResource("/estilos/controladorRegistro.fxml"));
      layoutPane.cargarPantalla("grafica",ControladorGrafica.class.getResource("/estilos/controladorGrafica.fxml"));
      layoutPane.mostrarComoPantallaActual("login");
      ventana.getIcons().add(new Image("/img/login.png")); 
      Scene marco = new Scene(layoutPane);
      marco.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
      marco.getStylesheets().add(Main.class.getResource("/estilos/estilos.css").toExternalForm());
      ventana.setScene(marco);
      ventana.setResizable(false);
      ventana.initStyle(StageStyle.TRANSPARENT);
      ventana.show();
    }
   
    public static void main(String[] args) {
        launch();
    }

}

