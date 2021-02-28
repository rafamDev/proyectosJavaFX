/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.print.ServiceUIFactory;

import modelo.UsuarioDAO;
import pojo.Usuario;


/**
 *
 * @author rafam
 */
public class ControladorRegistro extends ControladorConNavegabilidad implements Initializable{

    private Usuario usuario;
    
    private UsuarioDAO usuarioDAO;
    
    @FXML
    TextField nombre;
    
    @FXML
    TextField apellido;
    
    @FXML
    PasswordField password;
    
    @FXML
    ComboBox seccion;
    
    @FXML
    RadioButton radioSi;
    
    @FXML
    RadioButton radioNo;
    
    @FXML
    Text error;

    private boolean isAdministrador;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      this.usuarioDAO = new UsuarioDAO();
      this.crearIndicaciones();
      this.controlarRadioButton();
    }
    
    private RadioButton radioButtonSeleccionado(){
     ToggleGroup radioButtons = new ToggleGroup();
     this.radioSi.setToggleGroup(radioButtons);
     this.radioNo.setToggleGroup(radioButtons);
     RadioButton radioButtonSeleccionado = (RadioButton) radioButtons.getSelectedToggle();
     return radioButtonSeleccionado;
    }
    
    private void controlarRadioButton(){
      this.radioSi.disableProperty().bind(this.radioNo.selectedProperty());
      this.radioNo.disableProperty().bind(this.radioSi.selectedProperty()); 
    }
    
    private void crearIndicaciones(){
       this.seccion.setPromptText("Elija seccion");
       Tooltip tooltip = new Tooltip("Parametro obligatorio");
       Tooltip.install(this.nombre, tooltip);
       Tooltip.install(this.apellido, tooltip);
       Tooltip.install(this.password, tooltip);
       Tooltip.install(this.seccion, tooltip);
       Tooltip.install(this.radioSi, tooltip);
       Tooltip.install(this.radioNo, tooltip);
    }
    
    private boolean verificarCampos(){
      if(this.nombre.getText().equals("")){
        return false;
      }
      if(this.apellido.getText().equals("")){
        return false;
      }
      if(this.password.getText().equals("")){
        return false;
      }
      if(this.seccion.getSelectionModel().getSelectedItem().toString().equals("")){
        return false;
      }
      if(this.radioButtonSeleccionado() == null){
        return false;
      }
      
     return true;
    }
    
    private void crearUsuario(){
      this.usuario = new Usuario();
      this.usuario.setNombre(this.nombre.getText());
      this.usuario.setApellido(this.apellido.getText());
      this.usuario.setPassword(this.password.getText());
      this.usuario.setSeccion(this.seccion.getSelectionModel().getSelectedItem().toString());
      this.usuario.setAdministrador(false);
    }
  
    public void registrar(){
      if(verificarCampos()){  
         this.crearUsuario();
         this.usuarioDAO.insertarUsuario(this.usuario);
         this.dialogoInformacion();
         this.abrirLogin();
      }else{
         this.error.setText("Debe introducir sus datos en todos los campos");
      }  
    }
    
    public void cancelar(){
       this.abrirLogin();
    }
    
    private void abrirLogin(){
       Stage ventana = (Stage) this.layout.getScene().getWindow();
       ventana.hide();
       this.layout.mostrarComoPantallaActual("login");
       ventana = new Stage();
       ventana.setScene(this.layout.getScene());
       ventana.getIcons().add(new Image("/img/login.png")); 
       ventana.setResizable(false);
       ventana.show();
    }

   private void dialogoInformacion(){
      Alert dialogoInformacion = new Alert(Alert.AlertType.INFORMATION);
      dialogoInformacion.setTitle("Registro de usuarios");
      dialogoInformacion.setHeaderText(null);
      dialogoInformacion.setContentText("Usuario: " + this.nombre.getText() + " registrado con exito");
      dialogoInformacion.showAndWait();
   }

}
