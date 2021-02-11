/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modelo.UsuarioDAO;

/**
 *
 * @author rafam
 */
public class ControladorLogin extends ControladorConNavegabilidad {
    
    private UsuarioDAO usuarioDAO;
    
    @FXML
    TextField nombreUsuario;
    
    @FXML
    PasswordField password;
    
    @FXML
    Text error;
    
    public ControladorLogin(){
        this.usuarioDAO = new UsuarioDAO();
    }
    
    public void login(){
       boolean loginOK = usuarioDAO.verficarUsuario(nombreUsuario.getText(), password.getText());
       if(loginOK == true){
          Stage ventana = (Stage) this.layout.getScene().getWindow();
          ventana.hide();
          this.layout.mostrarComoPantallaActual("almacen");
          ventana = new Stage();
          ventana.setScene(this.layout.getScene());
          ventana.getIcons().add(new Image("/img/icono.png")); 
          ventana.show();
       }else{
          error.setText("Las credenciales ingresadas son incorrectas");
       }
    }
    
    public void cancelar(){
        Platform.exit();
    }

    public String getUsuario() {return this.nombreUsuario.getText();}

    public String getPassword() {return this.password.getText();}

}
