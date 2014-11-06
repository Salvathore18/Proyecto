/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Salvathore
 */
@Entity
@Table(name = "horarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Horarios.findAll", query = "SELECT h FROM Horarios h"),
    @NamedQuery(name = "Horarios.findById", query = "SELECT h FROM Horarios h WHERE h.id = :id"),
    @NamedQuery(name = "Horarios.findByCarnet", query = "SELECT h FROM Horarios h WHERE h.carnet = :carnet"),
    @NamedQuery(name = "Horarios.findByHorarioEntrada", query = "SELECT h FROM Horarios h WHERE h.horarioEntrada = :horarioEntrada"),
    @NamedQuery(name = "Horarios.findByHorarioSalida", query = "SELECT h FROM Horarios h WHERE h.horarioSalida = :horarioSalida")})
public class Horarios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 10)
    @Column(name = "carnet")
    private String carnet;
    @Column(name = "horario_entrada")
    @Temporal(TemporalType.TIME)
    private Date horarioEntrada;
    @Column(name = "horario_salida")
    @Temporal(TemporalType.TIME)
    private Date horarioSalida;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "horarios")
    private List<Marcar> marcarList;

    public Horarios() {
    }

    public Horarios(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public Date getHorarioEntrada() {
        return horarioEntrada;
    }

    public void setHorarioEntrada(Date horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }

    public Date getHorarioSalida() {
        return horarioSalida;
    }

    public void setHorarioSalida(Date horarioSalida) {
        this.horarioSalida = horarioSalida;
    }

    @XmlTransient
    public List<Marcar> getMarcarList() {
        return marcarList;
    }

    public void setMarcarList(List<Marcar> marcarList) {
        this.marcarList = marcarList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Horarios)) {
            return false;
        }
        Horarios other = (Horarios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Horarios[ id=" + id + " ]";
    }
    
}
