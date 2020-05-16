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
public class MatriculaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_matricula")
    private int idMatricula;
    @Basic(optional = false)
    @NotNull
    @Column(name = "persona_idusuario")
    private int personaIdusuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ficha_id_ficha")
    private int fichaIdFicha;

    public MatriculaPK() {
    }

    public MatriculaPK(int idMatricula, int personaIdusuario, int fichaIdFicha) {
        this.idMatricula = idMatricula;
        this.personaIdusuario = personaIdusuario;
        this.fichaIdFicha = fichaIdFicha;
    }

    public int getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(int idMatricula) {
        this.idMatricula = idMatricula;
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
        hash += (int) idMatricula;
        hash += (int) personaIdusuario;
        hash += (int) fichaIdFicha;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MatriculaPK)) {
            return false;
        }
        MatriculaPK other = (MatriculaPK) object;
        if (this.idMatricula != other.idMatricula) {
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
        return "model.MatriculaPK[ idMatricula=" + idMatricula + ", personaIdusuario=" + personaIdusuario + ", fichaIdFicha=" + fichaIdFicha + " ]";
    }
    
}
