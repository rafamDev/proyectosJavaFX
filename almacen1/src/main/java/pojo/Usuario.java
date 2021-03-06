
package pojo;

public class Usuario {
    
    private int codigo;
    private String nombre;
    private String apellido;
    private String password;
    private String seccion;
    private boolean isAdministrador;
    
    public Usuario(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
    }

    public Usuario() {}
    
    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSeccion() {
        return this.seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public void createAdministrador(String opcion) {
        if(opcion.equalsIgnoreCase("si")){
           this.isAdministrador = true;
        }
        if(opcion.equalsIgnoreCase("no")){
           this.isAdministrador = false;
        }
    }

    public void setAdministrador(boolean isAdministrador) {
        this.isAdministrador = isAdministrador;
    }

    public boolean getAdministrador() {
        return this.isAdministrador;
    }

    

}

