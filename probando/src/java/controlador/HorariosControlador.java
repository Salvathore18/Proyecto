/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;
import daos.HorariosFacade;
import Entidades.Horarios;
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
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author Salvathore
 */
@ManagedBean
@SessionScoped
public class HorariosControlador implements Serializable{
 private Horarios actual;
    private DataModel items = null;
    @EJB
    private HorariosFacade ejbFacade;
    private int selectItemIndex;
    private PaginacionHelper paginacion;

    public HorariosControlador() {
    }
    
    public Horarios getSelected(){
        if(actual == null){
            actual = new Horarios();
            selectItemIndex =-1;
        }
        return actual;
    }
    
    private HorariosFacade getFacade(){
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
    
    public static String getHoraActual() {
    Date ahora = new Date();
    SimpleDateFormat formateador = new SimpleDateFormat("hh:mm:ss");
    return formateador.format(ahora);
    }
    
    public String prepararLista(){
        recrearModelo();
        return "lista";
    }
    
    public String prepararVista(){
        actual =(Horarios)getItems().getRowData();
        selectItemIndex = paginacion.getPrimerItemPagina() + 
                getItems().getRowIndex();
        return "vista";
    }
    
    public String prepararCrear(){
        actual = new Horarios();
        selectItemIndex =-1;
        return "crear";
    }
    
    public String crear(){
        try {
            getFacade().create(actual);
            JsfUtil.agregarMensajeExito("Horario Creado");
            return prepararCrear();
        } catch (Exception e) {
            JsfUtil.agregarMensageDeError(e,"ERROR al Crear Horario");
            return null;
        }
    }
    
    public String prepararEditar(){
        actual = (Horarios)getItems().getRowData();
        selectItemIndex = paginacion.getPrimerItemPagina() + 
                getItems().getRowIndex();
        return "editar";
    }
    
    public String editar(){
        try {
            getFacade().edit(actual);
            JsfUtil.agregarMensajeExito("Horario Actualizado");
            return "vista";
        } catch (Exception e) {
            JsfUtil.agregarMensageDeError(e,"ERROR al editar Horario");
            return null;
        }
    }
    
    public String borrado(){
        actual = (Horarios)getItems().getRowData();
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
            JsfUtil.agregarMensajeExito("Horario Borrado");
        } catch (Exception e) {
            JsfUtil.agregarMensageDeError(e,"Error al borrar el Horario");
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
    
    @FacesConverter(forClass = Horarios.class)
    public static class HorarioscontroladorConversor implements Converter{
        @Override
        public Object getAsObject(
                FacesContext facescontext,
                UIComponent componente, 
                String valor
        ){
           if(valor == null || valor.length() == 0){
               return null;
           }
           HorariosControlador controlador = (HorariosControlador) facescontext.
                   getApplication().
                   getELResolver().
                   getValue(
                           facescontext.getELContext(), 
                           null, "horariosControlador"
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

            if(objeto instanceof Horarios){
                Horarios o = (Horarios) objeto;
                return getStringKey(o.getId());
            }else{
                Logger.getLogger(
                        this.getClass().getName()).log(Level.SEVERE,
                                "objeto {0} es de tipo {1}; tipo esperado {2}",
                                new Object[]{
                                    objeto,objeto.getClass().getName(),
                                    Horarios.class.getName()
                                });
                return null;
            }
        }
    }    
}
