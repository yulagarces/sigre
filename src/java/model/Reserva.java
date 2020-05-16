/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fabian
 */
@Entity
@Table(name = "reserva")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reserva.findAll", query = "SELECT r FROM Reserva r")
    , @NamedQuery(name = "Reserva.findByIdReserva", query = "SELECT r FROM Reserva r WHERE r.idReserva = :idReserva")
    , @NamedQuery(name = "Reserva.findByHoraInicio", query = "SELECT r FROM Reserva r WHERE r.horaInicio = :horaInicio")
    , @NamedQuery(name = "Reserva.findByHoraFin", query = "SELECT r FROM Reserva r WHERE r.horaFin = :horaFin")
    , @NamedQuery(name = "Reserva.findByFechaInicio", query = "SELECT r FROM Reserva r WHERE r.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Reserva.findByFechaFin", query = "SELECT r FROM Reserva r WHERE r.fechaFin = :fechaFin")
    , @NamedQuery(name = "Reserva.findByEstado", query = "SELECT r FROM Reserva r WHERE r.estado = :estado")})
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_reserva")
    private Integer idReserva;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hora_inicio")
    @Temporal(TemporalType.TIME)
    private Date horaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hora_fin")
    @Temporal(TemporalType.TIME)
    private Date horaFin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Column(name = "estado")
    private Integer estado;
    @JoinColumn(name = "ambiente_id_ambiente", referencedColumnName = "id_ambiente")
    @ManyToOne(fetch = FetchType.EAGER)
    private Ambiente ambienteIdAmbiente;
    @JoinColumn(name = "persona_idusuario", referencedColumnName = "persona_idusuario")
    @ManyToOne(fetch = FetchType.EAGER)
    private Persona personaIdusuario;
    @JoinColumn(name = "ficha_id_ficha", referencedColumnName = "id_ficha")
   @ManyToOne(fetch = FetchType.EAGER)
    private Ficha fichaIdFicha;

    public Reserva() {
    }

    public Reserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public Reserva(Integer idReserva, Date horaInicio, Date horaFin, Date fechaInicio, Date fechaFin) {
        this.idReserva = idReserva;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Ambiente getAmbienteIdAmbiente() {
        return ambienteIdAmbiente;
    }

    public void setAmbienteIdAmbiente(Ambiente ambienteIdAmbiente) {
        this.ambienteIdAmbiente = ambienteIdAmbiente;
    }

    public Persona getPersonaIdusuario() {
        return personaIdusuario;
    }

    public void setPersonaIdusuario(Persona personaIdusuario) {
        this.personaIdusuario = personaIdusuario;
    }

    public Ficha getFichaIdFicha() {
        return fichaIdFicha;
    }

    public void setFichaIdFicha(Ficha fichaIdFicha) {
        this.fichaIdFicha = fichaIdFicha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReserva != null ? idReserva.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reserva)) {
            return false;
        }
        Reserva other = (Reserva) object;
        if ((this.idReserva == null && other.idReserva != null) || (this.idReserva != null && !this.idReserva.equals(other.idReserva))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Reserva[ idReserva=" + idReserva + " ]";
    }
    
}
