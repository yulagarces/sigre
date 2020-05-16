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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fabian
 */
@Entity
@Table(name = "horario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Horario.findAll", query = "SELECT h FROM Horario h")
    , @NamedQuery(name = "Horario.findByIdHorario", query = "SELECT h FROM Horario h WHERE h.horarioPK.idHorario = :idHorario")
    , @NamedQuery(name = "Horario.findByFechaInicio", query = "SELECT h FROM Horario h WHERE h.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Horario.findByFechaFin", query = "SELECT h FROM Horario h WHERE h.fechaFin = :fechaFin")
    , @NamedQuery(name = "Horario.findByDia", query = "SELECT h FROM Horario h WHERE h.dia = :dia")
    , @NamedQuery(name = "Horario.findByHoraInicio", query = "SELECT h FROM Horario h WHERE h.horaInicio = :horaInicio")
    , @NamedQuery(name = "Horario.findByHoraFin", query = "SELECT h FROM Horario h WHERE h.horaFin = :horaFin")
    , @NamedQuery(name = "Horario.findByAmbienteIdAmbiente", query = "SELECT h FROM Horario h WHERE h.horarioPK.ambienteIdAmbiente = :ambienteIdAmbiente")
    , @NamedQuery(name = "Horario.findByPersonaIdusuario", query = "SELECT h FROM Horario h WHERE h.horarioPK.personaIdusuario = :personaIdusuario")
    , @NamedQuery(name = "Horario.findByFichaIdFicha", query = "SELECT h FROM Horario h WHERE h.horarioPK.fichaIdFicha = :fichaIdFicha")})
public class Horario implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HorarioPK horarioPK;
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "dia")
    private String dia;
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
    @JoinColumn(name = "ambiente_id_ambiente", referencedColumnName = "id_ambiente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Ambiente ambiente;
    @JoinColumn(name = "ficha_id_ficha", referencedColumnName = "id_ficha", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Ficha ficha;
    @JoinColumn(name = "persona_idusuario", referencedColumnName = "persona_idusuario", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Persona persona;

    public Horario() {
    }

    public Horario(HorarioPK horarioPK) {
        this.horarioPK = horarioPK;
    }

    public Horario(HorarioPK horarioPK, Date fechaInicio, Date fechaFin, String dia, Date horaInicio, Date horaFin) {
        this.horarioPK = horarioPK;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public Horario(int idHorario, int ambienteIdAmbiente, int personaIdusuario, int fichaIdFicha) {
        this.horarioPK = new HorarioPK(idHorario, ambienteIdAmbiente, personaIdusuario, fichaIdFicha);
    }

    public HorarioPK getHorarioPK() {
        return horarioPK;
    }

    public void setHorarioPK(HorarioPK horarioPK) {
        this.horarioPK = horarioPK;
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

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
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

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (horarioPK != null ? horarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Horario)) {
            return false;
        }
        Horario other = (Horario) object;
        if ((this.horarioPK == null && other.horarioPK != null) || (this.horarioPK != null && !this.horarioPK.equals(other.horarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Horario[ horarioPK=" + horarioPK + " ]";
    }
    
}
