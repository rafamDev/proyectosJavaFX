
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLconexion {

    private String ruta;
    private String usuario;
    private String password;
    
    public MySQLconexion(String bdd) {
       this.ruta = "jdbc:mysql://localhost:3306/" + bdd + "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Europe/Madrid";
       this.usuario = "root";
       this.password = "abc123";        
    }
    
    public Connection getMySQLconexion() {
        try {
            return DriverManager.getConnection(this.ruta, this.usuario, this.password);
        } catch (SQLException ex) {
            Logger.getLogger(MySQLconexion.class.getName()).log(Level.SEVERE, null, ex);
        }
      return null;
    }
    
}
