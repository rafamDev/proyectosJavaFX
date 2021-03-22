
package modelo;

import controlador.ControladorMercancia;
import controlador.Pdf;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pojo.Mercancia;

public class MercanciaDAO {
    
   private MySQLconexion mycon;
  
    public MercanciaDAO() {
     this.mycon = new MySQLconexion("almacen");
     this.crearTablaMercancia();
     this.crearTablaMercancia_eliminada();
   }
   
    private void crearTablaMercancia(){
      Connection con = null;
      Statement st = null; 
      String query = "CREATE TABLE IF NOT EXISTS mercancia" + "( codigo INTEGER NOT NULL auto_increment, " 
                + "nombre VARCHAR(255) NOT NULL," + "tipo VARCHAR(255) NOT NULL," 
                + "origen VARCHAR(255) NOT NULL," + "destino VARCHAR(255) NOT NULL," 
                + "naturaleza VARCHAR(255) NOT NULL," + "fecha_alta TIMESTAMP NOT NULL,"
                + "fecha_modificacion TIMESTAMP NULL," + "observaciones VARCHAR(255) NULL," 
                + "PRIMARY KEY(codigo)" + " )";
     
          try {
              con = this.mycon.getMySQLconexion();
              st = con.createStatement();
              st.executeUpdate(query);
          } catch (Exception ex) {
              Logger.getLogger(MercanciaDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
    
    private void crearTablaMercancia_eliminada(){
      Connection con = null;
      Statement st = null; 
      String query = "CREATE TABLE IF NOT EXISTS mercancia_eliminada" + "( id INTEGER NOT NULL auto_increment, " 
                + "codigo INTEGER NOT NULL, nombre VARCHAR(255) NOT NULL," + "tipo VARCHAR(255) NOT NULL," 
                + "origen VARCHAR(255) NOT NULL," + "destino VARCHAR(255) NOT NULL," + "naturaleza VARCHAR(255) NOT NULL,"
                + "fecha_alta TIMESTAMP NOT NULL, fecha_baja TIMESTAMP NOT NULL," + "fecha_modificacion TIMESTAMP NULL," 
                + "observaciones VARCHAR(255) NULL, PRIMARY KEY(id)" + " )";
        try {
              con = this.mycon.getMySQLconexion();
              st = con.createStatement();
              st.executeUpdate(query);
          } catch (Exception ex) {
              Logger.getLogger(MercanciaDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
      //Se compone mediante el siguiente TRIGGER:
         /* CREATE TRIGGER mercancia_eliminada_trigger_AD AFTER DELETE ON mercancia
            FOR EACH ROW 
            INSERT INTO mercancia_eliminada(codigo,nombre,tipo,origen,destino,naturaleza,fecha_alta,fecha_modificacion,fecha_baja,observaciones)
            VALUES(OLD.codigo,OLD.nombre,OLD.tipo,OLD.origen,OLD.destino,OLD.naturaleza,OLD.fecha_alta,OLD.fecha_modificacion,NOW(),OLD.observaciones);
         */
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

    public ArrayList<Mercancia> getMercanciasEliminadasBDD(){
      ArrayList<Mercancia> lista_mercancia = new ArrayList<Mercancia>();
        Connection conexion = null;
        Statement st = null;
        ResultSet rs = null;
         String query = "SELECT * FROM mercancia_eliminada ORDER BY codigo DESC";
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
                mercancia.setFecha_baja(rs.getTimestamp("fecha_baja"));
                mercancia.setObservaciones(rs.getString("observaciones"));
                lista_mercancia.add(mercancia);
             }
       } catch (SQLException ex) {
           Logger.getLogger(MercanciaDAO.class.getName()).log(Level.SEVERE, null, ex);
       }
    return lista_mercancia;
   }
    
    public void crearPDF_Actuales(){
       Pdf pdf = new Pdf("D:/pruebas/MercanciasActuales.pdf");
       pdf.setQuery("SELECT * FROM mercancia");
       pdf.setFecha_seleccionada("fecha_modificacion");
       pdf.generarPDF(this.mycon); 
    }
    
    public void crearPDF_Eliminadas(){
       Pdf pdf = new Pdf("D:/pruebas/MercanciasEliminadas.pdf");
       pdf.setQuery("SELECT * FROM mercancia_eliminada");
       pdf.setFecha_seleccionada("fecha_baja");
       pdf.generarPDF(this.mycon);
    }
}

