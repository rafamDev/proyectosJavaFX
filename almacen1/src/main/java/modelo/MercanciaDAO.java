/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;


import controlador.ControladorMercancia;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import pojo.Mercancia;

/**
 *
 * @author rafam
 */
public class MercanciaDAO {
    
   private MySQLconexion mycon;
  
    public MercanciaDAO() {
     this.mycon = new MySQLconexion("almacen");
     this.crearTabla();
   }
   
    private void crearTabla(){
      Connection con = null;
      Statement st = null; 
      String query = "CREATE TABLE IF NOT EXISTS mercancia" + "( codigo INTEGER NOT NULL auto_increment, " 
                + "nombre VARCHAR(255) NOT NULL," + "tipo VARCHAR(255) NOT NULL," 
                + "origen VARCHAR(255) NOT NULL," + "destino VARCHAR(255) NOT NULL," 
                + "naturaleza VARCHAR(255) NOT NULL," + "fecha_alta TIMESTAMP NOT NULL,"
                + "fecha_modificacion TIMESTAMP NULL," + "observaciones VARCHAR(255) NULL," 
                + "codigoUsuario INTEGER(4) NOT NULL," + "PRIMARY KEY(codigo)" + " )";
     
          try {
              con = this.mycon.getMySQLconexion();
              st = con.createStatement();
              st.executeUpdate(query);
          } catch (Exception ex) {
              Logger.getLogger(MercanciaDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
    }

    private Timestamp fecha_actual(){
      return new Timestamp(System.currentTimeMillis());
    }
    
    public void guardar(Mercancia mercancia){
     Connection con = null;
     PreparedStatement ps = null; 
     String insertQuery = "INSERT INTO mercancia(nombre,tipo,origen,destino,naturaleza,fecha_alta,observaciones)"
                         +"VALUES(?,?,?,?,?,?,?)";
          try {  
             con = this.mycon.getMySQLconexion();
             ps = con.prepareStatement(insertQuery);
             ps.setString(1, mercancia.getNombre());
             ps.setString(2, mercancia.getTipo());
             ps.setString(3, mercancia.getOrigen());  
             ps.setString(4, mercancia.getDestino());
             ps.setString(5, mercancia.getNaturaleza());
             ps.setTimestamp(6,this.fecha_actual());
             ps.setString(7, mercancia.getObservaciones());
             ps.executeUpdate();
        } catch (SQLException ex) {
           Logger.getLogger(MercanciaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizar(Mercancia mercancia){
     Connection con = null;
     PreparedStatement ps = null; 
     String updateQuery = "UPDATE mercancia SET nombre=?, tipo=?, origen=?, destino=?, naturaleza=?, fecha_modificacion=?, observaciones=? WHERE codigo=?";
        try {  
             con = this.mycon.getMySQLconexion();
             ps = con.prepareStatement(updateQuery);
             ps.setString(1, mercancia.getNombre());
             ps.setString(2, mercancia.getTipo());
             ps.setString(3, mercancia.getOrigen());  
             ps.setString(4, mercancia.getDestino());
             ps.setString(5, mercancia.getNaturaleza());
             ps.setTimestamp(6,this.fecha_actual());
             ps.setString(7,mercancia.getObservaciones());
             ps.setInt(8,ControladorMercancia.getCodigo()); 
             ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MercanciaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public void guardar_actualizar(Mercancia mercancia){
        if(ControladorMercancia.getCodigo() == 0){
          this.guardar(mercancia);
       }else{
          this.actualizar(mercancia);
       }
    }
   
    public void eliminar(){
     Connection con = null;
     PreparedStatement ps = null; 
     String deleteQuery = "DELETE FROM mercancia WHERE codigo=?";
      try {  
        con = this.mycon.getMySQLconexion();
        ps = con.prepareStatement(deleteQuery);
        ps.setInt(1,ControladorMercancia.getCodigo()); 
        ps.executeUpdate();
      }catch (SQLException ex) {
          Logger.getLogger(MercanciaDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
   } 
    
    public ArrayList<Mercancia> getMercanciasBDD(){
     ArrayList<Mercancia> lista_mercancia = new ArrayList<Mercancia>();
       Connection conexion = null;
        Statement st = null;
        ResultSet rs = null;
         String query = "SELECT * FROM mercancia ORDER BY codigo";
         conexion = this.mycon.getMySQLconexion();
       try {
           st = conexion.createStatement();
           rs = st.executeQuery(query);
             while(rs.next()){
              Mercancia mercancia = new Mercancia();
                mercancia.setCodigo(rs.getInt("codigo"));
                mercancia.setNombre(rs.getString("nombre"));
                mercancia.setTipo(rs.getString("tipo"));
                mercancia.setOrigen(rs.getString("origen"));
                mercancia.setDestino(rs.getString("destino"));
                mercancia.setNaturaleza(rs.getString("naturaleza"));
                mercancia.setFecha_alta(rs.getTimestamp("fecha_alta"));
                mercancia.setFecha_modificacion(rs.getTimestamp("fecha_modificacion"));
                mercancia.setObservaciones(rs.getString("observaciones"));
                lista_mercancia.add(mercancia);
             }
       } catch (SQLException ex) {
           Logger.getLogger(MercanciaDAO.class.getName()).log(Level.SEVERE, null, ex);
       }
    return lista_mercancia;
   }

}

