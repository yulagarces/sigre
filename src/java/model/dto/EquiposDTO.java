
package model.dto;

import model.Ambiente;
import model.Equipo;

/**
 *
 * @author santi
 */
public class EquiposDTO {
    
    private Ambiente ambiente;
    private Equipo equipo;

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
    
    
}
