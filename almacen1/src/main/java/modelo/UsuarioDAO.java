
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import pojo.Usuario;


public class UsuarioDAO {
    
    private MySQLconexion mycon;

    public UsuarioDAO(){
       this.mycon = new MySQLconexion("almacen");
       this.crearTabla();
    }
    
    private void crearTabla(){
      Connection con = null;
      Statement st = null; 
      String query = "CREATE TABLE IF NOT EXISTS usuario " + "( codigo INTEGER NOT NULL auto_increment," 
                + "nombre VARCHAR(255) NOT NULL," + "apellido VARCHAR(255) NOT NULL," 
                + "password VARCHAR(255) NOT NULL," + "seccion VARCHAR(255) NOT NULL,"
                + "administrador BOOLEAN NOT NULL," + "PRIMARY KEY(codigo)" + " )";
       try {
           con = this.mycon.getMySQLconexion();
           st = con.createStatement();
           st.executeUpdate(query);
        }catch (Exception ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public boolean verficarUsuario(String nombre, String password){
     Connection con = null;
     ResultSet rs = null; 
     PreparedStatement ps = null;
     String query = "SELECT * FROM usuario WHERE nombre=? && password=?";
     con = this.mycon.getMySQLconexion();
        try {  
            ps = con.prepareStatement(query);
            ps.setString(1,nombre);
            ps.setString(2,password);
            rs = ps.executeQuery();
              while(rs.next()){
                 if(rs.getString("nombre").equalsIgnoreCase(nombre) && rs.getString("password").equalsIgnoreCase(password)){
                    return true;
                 }
              }
        } catch (SQLException ex) {
           Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
     return false; 
   }  
    
   public void insertarUsuario(Usuario usuario){
     Connection con = null;
     PreparedStatement ps = null;
     String insertQuery = "INSERT INTO usuario(nombre,apellido,password,seccion,administrador)"
                         +"VALUES(?,?,?,?,?)";
          try {  
             con = this.mycon.getMySQLconexion();
             ps = con.prepareStatement(insertQuery);
             ps.setString(1,usuario.getNombre());
             ps.setString(2,usuario.getApellido());
             ps.setString(3,usuario.getPassword());
             ps.setString(4,usuario.getSeccion());
             ps.setBoolean(5,usuario.getAdministrador());
             ps.executeUpdate();
         } catch (SQLException ex) {
           Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
   } 
   
    public Map<String,Integer> contarEmpleadosPorSeccion(){
      List<Usuario> usuarios = this.buscarTodos();
      Map <String,Integer> usuariosPorSeccion = new HashMap<>();
      Connection con = null;    
        try {
            String query = "SELECT codigo, COUNT(*) AS cantidad FROM usuario GROUP BY codigo";
            con = this.mycon.getMySQLconexion();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            int codigo = 0;
            int cantidad = 0;
                while(rs.next()){
                    codigo = rs.getInt("codigo");
                    cantidad = rs.getInt("cantidad");
                        for(Usuario usuario: usuarios){
                            if(usuario.getCodigo() == codigo){
                               usuariosPorSeccion.put(usuario.getSeccion(),cantidad); 
                               break;
                            } 
                       }
                }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
      return usuariosPorSeccion;
    }

    private List<Usuario> buscarTodos() {
       List<Usuario> usuarios = new ArrayList<>();
       Connection con = null; 
        try {
            String query = "SELECT * FROM usuario ORDER BY codigo";
            con = this.mycon.getMySQLconexion();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
              while(rs.next()){
                Usuario usuario = new Usuario();
                usuario.setCodigo(rs.getInt("codigo"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setPassword(rs.getString("password"));
                usuario.setSeccion(rs.getString("seccion"));
                usuario.setAdministrador(rs.getBoolean("administrador"));
                usuarios.add(usuario);
             }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
       return usuarios;
    }
   
}
