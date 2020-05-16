/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author fabian
 */
@Embeddable
public class HorarioPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_horario")
    private int idHorario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ambiente_id_ambiente")
    private int ambienteIdAmbiente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "persona_idusuario")
    private int personaIdusuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ficha_id_ficha")
    private int fichaIdFicha;

    public HorarioPK() {
    }

    public HorarioPK(int idHorario, int ambienteIdAmbiente, int personaIdusuario, int fichaIdFicha) {
        this.idHorario = idHorario;
        this.ambienteIdAmbiente = ambienteIdAmbiente;
        this.personaIdusuario = personaIdusuario;
        this.fichaIdFicha = fichaIdFicha;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public int getAmbienteIdAmbiente() {
        return ambienteIdAmbiente;
    }

    public void setAmbienteIdAmbiente(int ambienteIdAmbiente) {
        this.ambienteIdAmbiente = ambienteIdAmbiente;
    }

    public int getPersonaIdusuario() {
        return personaIdusuario;
    }

    public void setPersonaIdusuario(int personaIdusuario) {
        this.personaIdusuario = personaIdusuario;
    }

    public int getFichaIdFicha() {
        return fichaIdFicha;
    }

    public void setFichaIdFicha(int fichaIdFicha) {
        this.fichaIdFicha = fichaIdFicha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idHorario;
        hash += (int) ambienteIdAmbiente;
        hash += (int) personaIdusuario;
        hash += (int) fichaIdFicha;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HorarioPK)) {
            return false;
        }
        HorarioPK other = (HorarioPK) object;
        if (this.idHorario != other.idHorario) {
            return false;
        }
        if (this.ambienteIdAmbiente != other.ambienteIdAmbiente) {
            return false;
        }
        if (this.personaIdusuario != other.personaIdusuario) {
            return false;
        }
        if (this.fichaIdFicha != other.fichaIdFicha) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.HorarioPK[ idHorario=" + idHorario + ", ambienteIdAmbiente=" + ambienteIdAmbiente + ", personaIdusuario=" + personaIdusuario + ", fichaIdFicha=" + fichaIdFicha + " ]";
    }
    
}
