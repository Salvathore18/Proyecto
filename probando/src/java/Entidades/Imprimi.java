/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import cone.conexion;
import java.sql.SQLException;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author Salvathore
 */
@Named(value = "imprimi")
@SessionScoped
public class Imprimi implements Serializable {
 
    conexion conn = new conexion();

    public Imprimi() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Creates a new instance of Imprimi
     */
   public void Imprimi(java.awt.event.ActionEvent evt) throws JRException, ClassNotFoundException, SQLException{
                    conn.conectar();
                    String dir = "C:\\Users\\angel_000\\Documents\\NetBeansProjects\\probando\\build\\web\\reporteempleado.jrxml";
                    JasperReport reporteJasper = JasperCompileManager.compileReport(dir);
                    JasperPrint mostrarReporte = JasperFillManager.fillReport(reporteJasper, null, conn.getConn());
                    JasperViewer.viewReport(mostrarReporte);
    }
    
}
