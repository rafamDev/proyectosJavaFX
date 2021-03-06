
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modelo.UsuarioDAO;


public class ControladorLogin extends ControladorConNavegabilidad implements Initializable{
    
    private UsuarioDAO usuarioDAO;
    
    @FXML
    TextField nombreUsuario;
    
    @FXML
    PasswordField password;
    
    @FXML
    Text error;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       this.usuarioDAO = new UsuarioDAO();
    }

    public void login(){
       boolean loginOK = this.usuarioDAO.verficarUsuario(this.nombreUsuario.getText(), this.password.getText());
       if(loginOK == true){
         this.abrirPantalla("almacen","/img/icono.png");
       }else{
         this.error.setText("Las credenciales ingresadas son incorrectas");
       }
    }
    
    public void registro(){
      this.abrirPantalla("registro","/img/icono.png");
    }
    
    public void cancelar(){
      Platform.exit();
    }

    private void abrirPantalla(String pantalla,String rutaImagen){
       Stage ventana = (Stage) this.layout.getScene().getWindow();
       ventana.hide();
       this.layout.mostrarComoPantallaActual(pantalla);
       ventana = new Stage();
       ventana.setScene(this.layout.getScene());
       ventana.getIcons().add(new Image(rutaImagen)); 
       ventana.setResizable(false);
       ventana.show();
    }

}
