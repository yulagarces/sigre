/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dto;

import model.Ambiente;
import model.Persona;
import model.PeticionEquipo;

/**
 *
 * @author fabian
 */
public class EquipoReservaDTO {
    
    private Persona persona;
    
    private Ambiente ambiente;
    
    private PeticionEquipo peticionequipo;

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public PeticionEquipo getPeticionequipo() {
        return peticionequipo;
    }

    public void setPeticionequipo(PeticionEquipo peticionequipo) {
        this.peticionequipo = peticionequipo;
    }


}
