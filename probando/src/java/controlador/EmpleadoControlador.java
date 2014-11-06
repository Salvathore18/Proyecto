/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;
import daos.EmpleadoFacade;
import Entidades.Empleado;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
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
@ManagedBean
@SessionScoped
public class EmpleadoControlador implements Serializable{
 private Empleado actual;

    private DataModel items = null;
    @EJB
    private EmpleadoFacade ejbFacade;
    private int selectItemIndex;
    private PaginacionHelper paginacion;

    public EmpleadoControlador() {
    }
    
    public Empleado getSelected(){
        if(actual == null){
            actual = new Empleado();
            selectItemIndex =-1;
        }
        return actual;
    }
    
    private EmpleadoFacade getFacade(){
        return ejbFacade;
    }
    
    public PaginacionHelper getPaginacion(){
        if(paginacion == null){
            paginacion = new PaginacionHelper(10){
                @Override
                public int getConteoItems(){
                    return getFacade().count();
                }
                @Override
                public DataModel crearPaginaModeloDeDatos(){
                    return new ListDataModel(getFacade().findRange(
                            new int[]{getPrimerItemPagina(),
                                getPrimerItemPagina()+getPaginasSize()})
                    );
                }
            };
        }
        return paginacion;
    }
    public void Imprimit(java.awt.event.ActionEvent evt) throws JRException, ClassNotFoundException, SQLException{
                    conexion conn = new conexion();            
                    conn.conectar();
                    String dir = "C:\\Users\\angel_000\\Documents\\NetBeansProjects\\probando\\build\\web\\reporteempleado.jrxml";
                    JasperReport reporteJasper = JasperCompileManager.compileReport(dir);
                    JasperPrint mostrarReporte = JasperFillManager.fillReport(reporteJasper, null, conn.getConn());
                    JasperViewer.viewReport(mostrarReporte);
                    conn = null;
    }
    
 
    
    public String prepararLista(){
        recrearModelo();
        return "lista";
    }
    
    public String prepararVista(){
        actual =(Empleado)getItems().getRowData();
        selectItemIndex = paginacion.getPrimerItemPagina() + 
                getItems().getRowIndex();
        return "vista";
    }
    
    public String prepararCrear(){
        actual = new Empleado();
        selectItemIndex =-1;
        return "crear";
    }
    
    public String crear(){
        try {
            getFacade().create(actual);
            JsfUtil.agregarMensajeExito("Empleado Creado");
            return prepararCrear();
        } catch (Exception e) {
            JsfUtil.agregarMensageDeError(e,"ERROR al Crear Empleado");
            return null;
        }
    }
    
    public String prepararEditar(){
        actual = (Empleado)getItems().getRowData();
        selectItemIndex = paginacion.getPrimerItemPagina() + 
                getItems().getRowIndex();
        return "editar";
    }
    
    public String editar(){
        try {
            getFacade().edit(actual);
            JsfUtil.agregarMensajeExito("Empleado Actualizado");
            return "vista";
        } catch (Exception e) {
            JsfUtil.agregarMensageDeError(e,"ERROR al editar Empleado");
            return null;
        }
    }
    
    public String borrado(){
        actual = (Empleado)getItems().getRowData();
        selectItemIndex = paginacion.getPrimerItemPagina() + 
                getItems().getRowIndex();
        realizarBorrado();
        recrearPaginacion();
        recrearModelo();
        return "lista";
    }
    
    public String borraryVer(){        
        recrearModelo();
        realizarBorrado();
        actualizarActualItem();
        
        if(selectItemIndex >=0){
            return "vista";
        }else{
            recrearModelo();
            return "lista";
        }
    }
    
    private void realizarBorrado(){
        try {
            getFacade().remove(actual);
            JsfUtil.agregarMensajeExito("Empleado Borrado");
        } catch (Exception e) {
            JsfUtil.agregarMensageDeError(e,"Error al borrar Empleado");
        }
    }
    
    private void actualizarActualItem(){
        int conteo = getFacade().count();
        
        if(selectItemIndex >= conteo){
            selectItemIndex = conteo -1;
            
            if(paginacion.getPrimerItemPagina() >=conteo){
                paginacion.anteriorPagina();
            }
        }
        if(selectItemIndex >= 0){
            actual = getFacade().findRange(
                    new int[]{selectItemIndex,
                        selectItemIndex+1}).get(0);
        }
    }
    
       
    public DataModel getItems(){
        if(items == null){
            items = getPaginacion().crearPaginaModeloDeDatos();
        }
        return items;
    }
    
    private void recrearModelo(){
        items = null;
    }
    
    private void recrearPaginacion(){
        paginacion = null;
    }
    
    private String siguiente(){
        getPaginacion().siguientePagina();
        recrearModelo();
        return "lista";
    }
    
    private String anterior(){
        getPaginacion().anteriorPagina();
        recrearModelo();
        return "lista";
    }
    
    public SelectItem[] obtenerItemsDisponiblesSeleccionandoMuchos()
    {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    } 
    
    public SelectItem[] obtenerItemsDisponiblesSeleccionandoUno()
    {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    } 
    
    @FacesConverter(forClass = Empleado.class)
    public static class EmpleadocontroladorConversor implements Converter{
        @Override
        public Object getAsObject(
                FacesContext facescontext,
                UIComponent componente, 
                String valor
        ){
           if(valor == null || valor.length() == 0){
               return null;
           }
           EmpleadoControlador controlador = (EmpleadoControlador) facescontext.
                   getApplication().
                   getELResolver().
                   getValue(
                           facescontext.getELContext(), 
                           null, "empleadoControlador"
                   );
           return controlador.getFacade().find(getKey(valor));
        }
    
    
        java.lang.Integer getKey(String valor){
            java.lang.Integer clave;
            clave = Integer.valueOf(valor);
            return clave;
        }
    
        String getStringKey(java.lang.Integer valor){
            StringBuilder sb = new StringBuilder();
            sb.append(valor);
            return sb.toString();
        }
    
        @Override
        public String getAsString(
                FacesContext facescontext,
                UIComponent componente,
                Object objeto
        ){
            if(objeto == null){
                return null;
            }

            if(objeto instanceof Empleado){
                Empleado o = (Empleado) objeto;
                return getStringKey(o.getId());
            }else{
                Logger.getLogger(
                        this.getClass().getName()).log(Level.SEVERE,
                                "objeto {0} es de tipo {1}; tipo esperado {2}",
                                new Object[]{
                                    objeto,objeto.getClass().getName(),
                                    Empleado.class.getName()
                                });
                return null;
            }
        }
    }    
}


