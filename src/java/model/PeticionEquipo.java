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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author santi
 */
@Entity
@Table(name = "peticion_equipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PeticionEquipo.findAll", query = "SELECT p FROM PeticionEquipo p")
    , @NamedQuery(name = "PeticionEquipo.findByIdPeticionEquipo", query = "SELECT p FROM PeticionEquipo p WHERE p.idPeticionEquipo = :idPeticionEquipo")
    , @NamedQuery(name = "PeticionEquipo.findByEstado", query = "SELECT p FROM PeticionEquipo p WHERE p.estado = :estado")
    , @NamedQuery(name = "PeticionEquipo.findByHoraInicio", query = "SELECT p FROM PeticionEquipo p WHERE p.horaInicio = :horaInicio")
    , @NamedQuery(name = "PeticionEquipo.findByHoraFin", query = "SELECT p FROM PeticionEquipo p WHERE p.horaFin = :horaFin")
    , @NamedQuery(name = "PeticionEquipo.findByFechaInicio", query = "SELECT p FROM PeticionEquipo p WHERE p.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "PeticionEquipo.findByDisponibilidad", query = "SELECT p FROM PeticionEquipo p WHERE p.disponibilidad = :disponibilidad")
    , @NamedQuery(name = "PeticionEquipo.findByFechaFinal", query = "SELECT p FROM PeticionEquipo p WHERE p.fechaFinal = :fechaFinal")})
public class PeticionEquipo implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "plazo_vencido")
    private int plazoVencido;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_peticion_equipo")
    private Integer idPeticionEquipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "estado")
    private String estado;
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
    @Column(name = "disponibilidad")
    private Integer disponibilidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_final")
    @Temporal(TemporalType.DATE)
    private Date fechaFinal;
    @JoinColumn(name = "equipo_id_equipo", referencedColumnName = "id_equipo")
    @ManyToOne(optional = false)
    private Equipo equipoIdEquipo;
    @JoinColumn(name = "ficha_idficha", referencedColumnName = "id_ficha")
    @ManyToOne
    private Ficha fichaIdficha;
    @JoinColumn(name = "persona_idusuario", referencedColumnName = "persona_idusuario")
    @ManyToOne(optional = false)
    private Persona personaIdusuario;

    public PeticionEquipo() {
    }

    public PeticionEquipo(Integer idPeticionEquipo) {
        this.idPeticionEquipo = idPeticionEquipo;
    }

    public PeticionEquipo(Integer idPeticionEquipo, String estado, Date horaInicio, Date horaFin, Date fechaInicio, Date fechaFinal) {
        this.idPeticionEquipo = idPeticionEquipo;
        this.estado = estado;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
    }

    public Integer getIdPeticionEquipo() {
        return idPeticionEquipo;
    }

    public void setIdPeticionEquipo(Integer idPeticionEquipo) {
        this.idPeticionEquipo = idPeticionEquipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public Integer getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(Integer disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Equipo getEquipoIdEquipo() {
        return equipoIdEquipo;
    }

    public void setEquipoIdEquipo(Equipo equipoIdEquipo) {
        this.equipoIdEquipo = equipoIdEquipo;
    }

    public Ficha getFichaIdficha() {
        return fichaIdficha;
    }

    public void setFichaIdficha(Ficha fichaIdficha) {
        this.fichaIdficha = fichaIdficha;
    }

    public Persona getPersonaIdusuario() {
        return personaIdusuario;
    }

    public void setPersonaIdusuario(Persona personaIdusuario) {
        this.personaIdusuario = personaIdusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPeticionEquipo != null ? idPeticionEquipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PeticionEquipo)) {
            return false;
        }
        PeticionEquipo other = (PeticionEquipo) object;
        if ((this.idPeticionEquipo == null && other.idPeticionEquipo != null) || (this.idPeticionEquipo != null && !this.idPeticionEquipo.equals(other.idPeticionEquipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PeticionEquipo[ idPeticionEquipo=" + idPeticionEquipo + " ]";
    }

    public int getPlazoVencido() {
        return plazoVencido;
    }

    public void setPlazoVencido(int plazoVencido) {
        this.plazoVencido = plazoVencido;
    }
    
}
