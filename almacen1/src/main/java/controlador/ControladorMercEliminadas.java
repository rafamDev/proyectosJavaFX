
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import modelo.MercanciaDAO;
import pojo.Mercancia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

public class ControladorMercEliminadas implements Initializable{
   
    @FXML
    TableView<Mercancia> tabla_mercanciasEliminadas;
    
    private MercanciaDAO mercanciaDAO; 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      this.mercanciaDAO = new MercanciaDAO();
      this.cargarMercanciasEliminadasBDD();
    }
    
    private void cargarMercanciasEliminadasBDD(){
      ObservableList<Mercancia> mercanciasObservables = FXCollections.observableArrayList();
      ArrayList<Mercancia> lista_mercancia = this.mercanciaDAO.getMercanciasEliminadasBDD();
      mercanciasObservables.addAll(lista_mercancia);
      this.tabla_mercanciasEliminadas.setItems(mercanciasObservables);
    }
}
