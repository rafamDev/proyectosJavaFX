/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;


import controlador.ControladorLogin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rafam
 */
public class UsuarioDAO {
    
    private MySQLconexion mycon;
    private ControladorLogin login;
    
    public UsuarioDAO(){
       this.mycon = new MySQLconexion("almacen");
       this.crearTabla();
    }
    
    private void crearTabla(){
      Connection con = null;
      Statement st = null; 
      String query = "CREATE TABLE IF NOT EXISTS usuarios" + "( codigo INTEGER NOT NULL auto_increment, " 
                + "usuario VARCHAR(255) NOT NULL," + " password VARCHAR(255) NOT NULL," 
                + "PRIMARY KEY(codigo)" + " )";
       try {
           con = this.mycon.getMySQLconexion();
           st = con.createStatement();
           st.executeUpdate(query);
        }catch (Exception ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public boolean verficarUsuario(String usuario, String password){
     Connection con = null;
     ResultSet rs = null; 
     PreparedStatement ps = null;
     String query = "SELECT * FROM usuarios WHERE usuario=? && password=?";
     con = this.mycon.getMySQLconexion();
        try {  
            ps = con.prepareStatement(query);
            ps.setString(1,usuario);
            ps.setString(2,password);
            rs = ps.executeQuery();
              while(rs.next()){
                 if(rs.getString("usuario").equalsIgnoreCase(usuario) && rs.getString("password").equalsIgnoreCase(password)){
                    return true;
                 }
              }
        } catch (SQLException ex) {
           Logger.getLogger(MercanciaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
     return false; 
   }  
    
   public void insertarUsuario(){
      this.login = new ControladorLogin();
      boolean existeUsuario = verficarUsuario(this.login.getUsuario(),this.login.getPassword());
       if(!existeUsuario){
          Connection con = null;
          PreparedStatement ps = null; 
          String insertQuery = "INSERT INTO usuario(usuario,password)"
                         +"VALUES(?,?)";
          try {  
             con = this.mycon.getMySQLconexion();
             ps = con.prepareStatement(insertQuery);
             ps.setString(1, this.login.getUsuario());
             ps.setString(2, this.login.getPassword());
             ps.executeUpdate();
         } catch (SQLException ex) {
           Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
       }
   } 
}
