
package controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.MercanciaDAO;
import pojo.Mercancia;

public class ControladorMercancia extends ControladorConNavegabilidad implements Initializable{
    @FXML
    TextField txtCodigo;
    
    @FXML
    TextField txtNombre;
    
    @FXML
    ComboBox comboTipo;
    
    @FXML
    TextField txtOrigen;
    
    @FXML
    TextField txtDestino;
   
    @FXML
    RadioButton radioPerecedera;
    
    @FXML
    RadioButton radioPerdurable;
    
    @FXML
    TextField txtFecha_alta;
    
    @FXML
    TextField txtFecha_modificacion;
     
    @FXML
    TextArea txtObservaciones;
    
    @FXML
    TableView<Mercancia> tabla_mercancias;
    
    @FXML
    Button btnModificar;
    
    @FXML
    Button btnEliminar;
    
    private Mercancia mercancia;
    
    private MercanciaDAO mercanciaDAO;
    
    private static int codigo = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       this.mercanciaDAO = new MercanciaDAO();
       this.cargarMercanciasBDD();
       this.estadoBotones();
       this.crearIndicaciones();
       this.controlarRadioButton();
    }
  
    private RadioButton radioButtonSeleccionado(){
     ToggleGroup radioButtons = new ToggleGroup();
     this.radioPerecedera.setToggleGroup(radioButtons);
     this.radioPerdurable.setToggleGroup(radioButtons);
     RadioButton radioButtonSeleccionado = (RadioButton) radioButtons.getSelectedToggle();
     return radioButtonSeleccionado;
    }

    private void controlarRadioButton(){
      this.radioPerecedera.disableProperty().bind(this.radioPerdurable.selectedProperty());
      this.radioPerdurable.disableProperty().bind(this.radioPerecedera.selectedProperty()); 
    }
    
    private void dialogoInformacion(String funcion){
       Alert dialogoInformacion = new Alert(Alert.AlertType.INFORMATION);
        if(funcion.equals("guardar")){
            dialogoInformacion.setTitle("Mercancia Guardada");
            dialogoInformacion.setHeaderText(null);
            dialogoInformacion.setContentText("Mercancia exitosamente guardada");
            dialogoInformacion.showAndWait();
        }
        if(funcion.equals("eliminar")){
            dialogoInformacion.setTitle("Mercancia Eliminada");
            dialogoInformacion.setHeaderText(null);
            dialogoInformacion.setContentText("Mercancia con codigo: " + codigo + " exitosamente eliminada");
            dialogoInformacion.showAndWait();
        }
        if(funcion.equals("modificar")){
            dialogoInformacion.setTitle("Mercancia modificada");
            dialogoInformacion.setHeaderText(null);
            dialogoInformacion.setContentText("Mercancia con codigo: " + codigo + " exitosamente modificada");
            dialogoInformacion.showAndWait();
        }
        if(funcion.equals("error")){
            dialogoInformacion.setTitle("Error");
            dialogoInformacion.setHeaderText(null);
            dialogoInformacion.setContentText("Inserte los datos en los parametros solicitados");
            dialogoInformacion.showAndWait();
        }
    }
    
    private void crearMercancia(){
       this.mercancia = new Mercancia();
         mercancia.setNombre(this.txtNombre.getText());
         mercancia.setTipo(this.comboTipo.getSelectionModel().getSelectedItem().toString());
         mercancia.setOrigen(this.txtOrigen.getText());
         mercancia.setDestino(this.txtDestino.getText());
         mercancia.setNaturaleza(this.radioButtonSeleccionado().getText());
         mercancia.setObservaciones(this.txtObservaciones.getText());
    }
    
    private void crearIndicaciones(){
       this.comboTipo.setPromptText("Elija tipo de mercancia");
       Tooltip tooltip = new Tooltip("Parametro obligatorio");
       Tooltip.install(this.txtNombre, tooltip);
       Tooltip.install(this.comboTipo, tooltip);
       Tooltip.install(this.txtOrigen, tooltip);
       Tooltip.install(this.txtDestino, tooltip);
       Tooltip.install(this.radioPerecedera, tooltip);
       Tooltip.install(this.radioPerdurable, tooltip);
    }
   
    private void limpiarCampos(){
       this.txtCodigo.clear();
       this.txtNombre.clear();
       this.comboTipo.setValue("");
       this.radioPerecedera.setSelected(false);
       this.radioPerdurable.setSelected(false);
       this.txtOrigen.clear();
       this.txtDestino.clear();
       this.txtFecha_alta.clear();
       this.txtFecha_modificacion.clear();
       this.txtObservaciones.clear();
    }
    
    private boolean verificarCampos(){
      if(this.txtNombre.getText().equals("")){
        return false;
      }
      if(this.comboTipo.getSelectionModel().getSelectedItem().toString().equals("")){
        return false;
      }
      if(this.txtOrigen.getText().equals("")){
        return false;
      }
      if(this.txtDestino.getText().equals("")){
        return false;
      }
      if(this.radioButtonSeleccionado() == null){
        return false;
      }
     return true;
    }
    
    public void guardar(){
      if(this.verificarCampos() == true){ 
         Alert dialogoGuardar = new Alert(Alert.AlertType.CONFIRMATION);
         dialogoGuardar.setHeaderText(null);
         dialogoGuardar.setTitle("Confirmación");
         dialogoGuardar.setContentText("¿Estas seguro de guardar esta mercancia?");
         Optional<ButtonType> accion = dialogoGuardar.showAndWait();
           if(accion.get() == ButtonType.OK){
              this.crearMercancia();
              this.mercanciaDAO.guardar_actualizar(this.mercancia);
                if(codigo != 0){
                   this.dialogoInformacion("modificar");
                   codigo = 0;
                }else{
                   this.dialogoInformacion("guardar");
                }
              this.cargarMercanciasBDD();
              this.limpiarCampos();
              this.desactivarBotones();
           }else{
              this.limpiarCampos();
              this.desactivarBotones();
              dialogoGuardar.close();
           }   
           this.desactivarBotones();
      }else{
        this.dialogoInformacion("error");
      }
    }

    private void cargarMercanciasBDD(){
      ObservableList<Mercancia> mercanciasObservables = FXCollections.observableArrayList();
      ArrayList<Mercancia> lista_mercancia = this.mercanciaDAO.getMercanciasBDD();
      mercanciasObservables.addAll(lista_mercancia);
      this.tabla_mercancias.setItems(mercanciasObservables);
    }
 
    private void activarRadioButton(){
     //Primero los desactivo y despues activo el indicado.
     this.radioPerecedera.setSelected(false);
     this.radioPerdurable.setSelected(false);
       if(mercancia.getNaturaleza().equals("Perecedera")){
          this.radioPerecedera.setSelected(true);
        }
       if(mercancia.getNaturaleza().equals("Perdurable")){
          this.radioPerdurable.setSelected(true);
       }
    }
    
    private void rellenarCampos(){
      this.txtCodigo.setText("" + mercancia.getCodigo());
      this.txtNombre.setText(mercancia.getNombre());
      this.comboTipo.setValue(mercancia.getTipo());
      this.txtOrigen.setText(mercancia.getOrigen());
      this.txtDestino.setText(mercancia.getDestino());
      this.activarRadioButton();
      this.radioButtonSeleccionado();
      this.txtFecha_alta.setText("" + mercancia.getFecha_alta());
        if(mercancia.getFecha_modificacion() != null){ 
          this.txtFecha_modificacion.setText("" + mercancia.getFecha_modificacion());
        } else { 
          this.txtFecha_modificacion.setText("");
        }
      this.txtObservaciones.setText(mercancia.getObservaciones());
    }
    
    private void desactivarBotones(){
      btnModificar.setDisable(true);
      btnEliminar.setDisable(true); 
    }
   
    private void estadoBotones(){
      this.desactivarBotones();
      this.tabla_mercancias.setOnMouseClicked((MouseEvent event) -> {
       if(event.getClickCount() > 1){
        if(tabla_mercancias.getSelectionModel().getSelectedItem() != null){ 
           mercancia = tabla_mercancias.getSelectionModel().getSelectedItem(); 
             btnModificar.setDisable(false);
             btnEliminar.setDisable(false);
          }
        }
      });
    }
    
    public void modificar(){
       Alert dialogoModificar = new Alert(Alert.AlertType.CONFIRMATION);
       dialogoModificar.setHeaderText(null);
       dialogoModificar.setTitle("Confirmación");
       dialogoModificar.setContentText("¿Estas seguro de modificar esta mercancia?");
       Optional<ButtonType> accion = dialogoModificar.showAndWait();
         if(accion.get() == ButtonType.OK) {
            this.rellenarCampos();
            codigo = Integer.parseInt(this.txtCodigo.getText());
            dialogoModificar.close();
         }else{
          dialogoModificar.close();
        }
        this.desactivarBotones(); 
    }

    public void eliminar(){
        Alert dialogoEliminar = new Alert(Alert.AlertType.CONFIRMATION);
        dialogoEliminar.setHeaderText(null);
        dialogoEliminar.setTitle("Confirmación");
        dialogoEliminar.setContentText("¿Estas seguro de dar de baja esta mercancia?");
        Optional<ButtonType> accion = dialogoEliminar.showAndWait();
          if(accion.get() == ButtonType.OK) {
            this.rellenarCampos();
            codigo = Integer.parseInt(this.txtCodigo.getText());
            this.mercanciaDAO.eliminar();
            this.dialogoInformacion("eliminar");
            this.cargarMercanciasBDD();
            this.limpiarCampos();
            this.desactivarBotones();
          }else{
            this.limpiarCampos();
            this.desactivarBotones();  
            dialogoEliminar.close();
          }
          this.desactivarBotones();
    }
    
    public void mostrarPantallaChart(){
        HiloVentana hiloGrafica = new HiloVentana("Secciones");
        hiloGrafica.setRutaFXML("/estilos/controladorGrafica.fxml");
        hiloGrafica.setRuta_icono("/img/icono.png");
        hiloGrafica.run();
    }
   
    public void mostrarMercanciasEliminadas(){
       HiloVentana hiloMercEliminadas = new HiloVentana("Mercancias eliminadas");
       hiloMercEliminadas.setRutaFXML("/estilos/controladorMercEliminadas.fxml");
       hiloMercEliminadas.setRuta_icono("/img/borrar.png");
       hiloMercEliminadas.run();
    }
    
    public void cambiarDeUsuario(){
       Stage ventana = (Stage) this.layout.getScene().getWindow();
       ventana.hide();
       this.layout.mostrarComoPantallaActual("login");
       ventana = new Stage();
       ventana.setScene(this.layout.getScene());
       ventana.getIcons().add(new Image("/img/login.png")); 
       ventana.setResizable(false);
       ventana.initStyle(StageStyle.UNDECORATED);
       ventana.show();
    }
     
     public static int getCodigo() {
        return codigo;
     }

}
