/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Programa;

import service.ProgramaEJB;

/**
 *
 * @author santi
 */
@Named(value = "programaBean")

@ViewScoped
public class ProgramaBean implements Serializable {

    private Programa programa;
    private List<Programa> programas;
    private Programa programasSeleccionado;
    private Programa prograSeleccionado;
    @EJB //Pone a disposicion JPA
    ProgramaEJB ProgramaEJB;
    @Inject
    ProgramaEJB programaEJB;

    @PostConstruct
    public void init() {
        programa = new Programa();
        programas = initListPrograma();
    }

    public List<Programa> initListPrograma() {

        return ProgramaEJB.findAll();
    }

    public void crearPrograma() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            programaEJB.crearPrograma(programa);
            programas.clear();
            context.addMessage(null, new FacesMessage("Registrado", "Correctamente el programa: " + programa.getNombre()));
            programa = new Programa();
            programas = ProgramaEJB.findAll();
            programas = initListPrograma();

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("Error al registrar"));
        }

    }

    /*update programa*/
    public void updatePrograma(Programa p) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (ProgramaEJB.updatePrograma(p).getIdPrograma() != null) {
            context.addMessage(null, new FacesMessage("Actualizado correctamente: ",""));
        } else {
            context.addMessage(null, new FacesMessage("Error", "al actualizar"));
        }
    }

    public void deletePrograma(Programa p) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (ProgramaEJB.deletePrograma(p)) {

            context.addMessage(null, new FacesMessage("Eliminado correctamente: " + p.getNombre()));
            programas = ProgramaEJB.findAll();
        } else {
            context.addMessage(null, new FacesMessage("Error", "al eliminar"));
        }
    }

    public int contarProg() {
        return ProgramaEJB.contarProgramas();
    }

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    public List<Programa> getProgramas() {
        return programas;
    }

    public void setProgramas(List<Programa> programas) {
        this.programas = programas;
    }

    public Programa getProgramasSeleccionado() {
        return programasSeleccionado;
    }

    public void setProgramasSeleccionado(Programa programasSeleccionado) {
        this.programasSeleccionado = programasSeleccionado;
    }

    public Programa getPrograSeleccionado() {
        return prograSeleccionado;
    }

    public void setPrograSeleccionado(Programa prograSeleccionado) {
        this.prograSeleccionado = prograSeleccionado;
    }

}
