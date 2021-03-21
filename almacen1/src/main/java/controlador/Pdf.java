package controlador;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.MySQLconexion;

public class Pdf {
    
    private String ruta;
    private String fecha_seleccionada;
    private String nombre_fecha;
    private String query;
   
    public Pdf(String ruta){
      this.ruta = ruta;
    }

    public void setFecha_seleccionada(String fecha_seleccionada) {
      this.fecha_seleccionada = fecha_seleccionada;
        if(this.fecha_seleccionada.equalsIgnoreCase("fecha_baja")){
           this.nombre_fecha = "Fecha de baja";
        }
        if(this.fecha_seleccionada.equalsIgnoreCase("fecha_modificacion")){
           this.nombre_fecha = "Fecha de Modificacion";
        }
    }
    
    public void setQuery(String query) {
        this.query = query;
    }
 
    public void generarPDF(MySQLconexion myCon){
      Connection con = null;
      Statement st = null;
      ResultSet rs = null;
      FileOutputStream ficheroPDF = null;
        try {
            ficheroPDF = new FileOutputStream(new File(this.ruta));
            Document document = new Document();
            PdfWriter.getInstance(document, ficheroPDF);
            document.open();
             con = myCon.getMySQLconexion();
             st = con.createStatement();
             rs = st.executeQuery(this.query);
            document.add(this.encabezado());
            document.add(this.tabla(rs));
            document.close();
            st.close();
            con.close();
        } catch (DocumentException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private PdfPTable encabezado() {
       PdfPTable tabla1 = new PdfPTable(12);
       tabla1.setWidthPercentage(100);
          PdfPCell celda = null;
            celda = new PdfPCell(new Phrase("Codigo"));
            tabla1.addCell(celda);
            celda = new PdfPCell(new Phrase("Nombre"));
            celda.setColspan(3);
            tabla1.addCell(celda);
            celda = new PdfPCell(new Phrase("Tipo"));
            tabla1.addCell(celda);
            celda = new PdfPCell(new Phrase("Origen"));
            celda.setColspan(2);
            tabla1.addCell(celda);
            celda = new PdfPCell(new Phrase("Destino"));
            celda.setColspan(2);
            tabla1.addCell(celda);
            celda = new PdfPCell(new Phrase(this.nombre_fecha));
            celda.setColspan(4);
            tabla1.addCell(celda); 
        return tabla1;    
    }

    private PdfPTable tabla(ResultSet rs) {
       PdfPCell celda = null;
       PdfPTable tabla2 = new PdfPTable(12);
       tabla2.setWidthPercentage(100);
        try {
            while(rs.next()){
                int codigo = rs.getInt("codigo");
                celda = new PdfPCell(new Phrase(codigo + ""));
                tabla2.addCell(celda);
                String nombre = rs.getString("nombre");
                celda = new PdfPCell(new Phrase(nombre));
                celda.setColspan(3);
                tabla2.addCell(celda);
                String tipo = rs.getString("tipo");
                celda = new PdfPCell(new Phrase(tipo));
                tabla2.addCell(celda);
                String origen = rs.getString("origen");
                celda = new PdfPCell(new Phrase(origen));
                celda.setColspan(2);
                tabla2.addCell(celda);
                String destino = rs.getString("destino");
                celda = new PdfPCell(new Phrase(destino));
                celda.setColspan(2);
                tabla2.addCell(celda);
                String fecha = rs.getString(this.fecha_seleccionada);
                celda = new PdfPCell(new Phrase(fecha));
                celda.setColspan(4);
                tabla2.addCell(celda);
            }
            rs.close();   
        } catch (SQLException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
       return tabla2; 
    }
 
}
