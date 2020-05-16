/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author fabian
 */
@Entity
@Table(name = "ambiente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ambiente.findAll", query = "SELECT a FROM Ambiente a")
    , @NamedQuery(name = "Ambiente.findByIdAmbiente", query = "SELECT a FROM Ambiente a WHERE a.idAmbiente = :idAmbiente")
    , @NamedQuery(name = "Ambiente.findByNombre", query = "SELECT a FROM Ambiente a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "Ambiente.findByTipo", query = "SELECT a FROM Ambiente a WHERE a.tipo = :tipo")
    , @NamedQuery(name = "Ambiente.findByCapacidad", query = "SELECT a FROM Ambiente a WHERE a.capacidad = :capacidad")
    , @NamedQuery(name = "Ambiente.findByEquipos", query = "SELECT a FROM Ambiente a WHERE a.equipos = :equipos")
    , @NamedQuery(name = "Ambiente.findByDescripcion", query = "SELECT a FROM Ambiente a WHERE a.descripcion = :descripcion")})
public class Ambiente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ambiente")
    private Integer idAmbiente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "capacidad")
    private int capacidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "Equipos")
    private String equipos;
    @Size(max = 300)
    @Column(name = "Descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ambiente", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Horario> horarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ambienteIdAmbiente", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Equipo> equipoList;
    @OneToMany(mappedBy = "ambienteIdAmbiente", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Reserva> reservaList;

    public Ambiente() {
    }

    public Ambiente(Integer idAmbiente) {
        this.idAmbiente = idAmbiente;
    }

    public Ambiente(Integer idAmbiente, String nombre, String tipo, int capacidad, String equipos) {
        this.idAmbiente = idAmbiente;
        this.nombre = nombre;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.equipos = equipos;
    }

    public Integer getIdAmbiente() {
        return idAmbiente;
    }

    public void setIdAmbiente(Integer idAmbiente) {
        this.idAmbiente = idAmbiente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getEquipos() {
        return equipos;
    }

    public void setEquipos(String equipos) {
        this.equipos = equipos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Horario> getHorarioList() {
        return horarioList;
    }

    public void setHorarioList(List<Horario> horarioList) {
        this.horarioList = horarioList;
    }

    @XmlTransient
    public List<Equipo> getEquipoList() {
        return equipoList;
    }

    public void setEquipoList(List<Equipo> equipoList) {
        this.equipoList = equipoList;
    }

    @XmlTransient
    public List<Reserva> getReservaList() {
        return reservaList;
    }

    public void setReservaList(List<Reserva> reservaList) {
        this.reservaList = reservaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAmbiente != null ? idAmbiente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ambiente)) {
            return false;
        }
        Ambiente other = (Ambiente) object;
        if ((this.idAmbiente == null && other.idAmbiente != null) || (this.idAmbiente != null && !this.idAmbiente.equals(other.idAmbiente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Ambiente[ idAmbiente=" + idAmbiente + " ]";
    }
    
}