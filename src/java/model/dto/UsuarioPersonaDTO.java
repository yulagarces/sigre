/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dto;

import model.Centro;
import model.Persona;
import model.Usuario;

/**
 *
 * @author fabian
 */
public class UsuarioPersonaDTO {
    
     private Usuario usuarios;
    
    private Persona personas;
    
    private Centro centros;
   
    
    
    public Usuario getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuario usuarios) {
        this.usuarios = usuarios;
    }

    public Persona getPersonas() {
        return personas;
    }

    public void setPersonas(Persona personas) {
        this.personas = personas;
    }

    public Centro getCentros() {
        return centros;
    }

    public void setCentros(Centro centros) {
        this.centros = centros;
    }
    
    
}
