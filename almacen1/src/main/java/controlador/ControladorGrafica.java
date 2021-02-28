/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import modelo.UsuarioDAO;

/**
 *
 * @author rafam
 */
public class ControladorGrafica extends ControladorConNavegabilidad implements Initializable{

    private UsuarioDAO usuarioDAO;
    
    @FXML
    private PieChart pieChart;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.usuarioDAO = new UsuarioDAO();
        this.cargarDatosEnElChart();
    }
    
    public void cargarDatosEnElChart(){
      Map<String,Integer> usuarioPorSeccion = this.usuarioDAO.contarEmpleadosPorSeccion();
      ObservableList<PieChart.Data> datosParaElchart = FXCollections.observableArrayList();
        usuarioPorSeccion.forEach((nombreSeccion,cantidad) -> {
           PieChart.Data data = new PieChart.Data(nombreSeccion,cantidad);
           datosParaElchart.add(data);
        });
      this.pieChart.setData(datosParaElchart);
    }
}
