/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import Entidades.Descuento;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Salvathore
 */
@Stateless
public class DescuentoFacade extends AbstractFacade<Descuento> {
    @PersistenceContext(unitName = "probandoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DescuentoFacade() {
        super(Descuento.class);
    }
    
}
