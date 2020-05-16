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
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import model.Ambiente;
import service.AmbientesEJB;

/**
 *
 * @author santi
 */
@Named(value = "ambientesBean")
@ViewScoped
public class AmbientesBean implements Serializable {

    private Ambiente ambiente;
    private List<Ambiente> ambientes;
    private Ambiente ambienteSeleccionado;
    private Ambiente eliminarseleccionado;

    @EJB // pone a dispocision Servicio
    AmbientesEJB AmbienteEJB;

    public AmbientesBean() {
    }

    @PostConstruct

    public void inicio() {
        ambiente = new Ambiente();
        ambientes = initListAmbiente();
    }

    public List<Ambiente> initListAmbiente() {

        return AmbienteEJB.findAll();

    }

    public int contarAmbientes() {

        return AmbienteEJB.countAmbientes();
    }

    public void createAmbiente() {

        FacesContext context = FacesContext.getCurrentInstance();

        try {
            AmbienteEJB.createAmbiente(ambiente);
            ambientes.clear();
            context.addMessage(null, new FacesMessage("Registrado", "Correctamente el ambiente: " + ambiente.getNombre()));

            ambientes = AmbienteEJB.findAll();

            ambiente = new Ambiente();

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("Error al registrar", e.getMessage()));
        }
    }

    public void updateAmbiente(Ambiente a) {

        FacesContext context = FacesContext.getCurrentInstance();

        if (AmbienteEJB.updateAmbiente(a).getIdAmbiente() != null) {
            context.addMessage(null, new FacesMessage("", "Actualización correcta del ambiente: " + a.getNombre()));
        } else {
            context.addMessage(null, new FacesMessage("Error", " al actualizar"));
        }

    }

    public void deleteAmbiente(Ambiente a) {

        FacesContext context = FacesContext.getCurrentInstance();

        if (AmbienteEJB.deleteAmbiente(a)) {
            ambientes.clear();
            context.addMessage(null, new FacesMessage("Eliminación correcta del ambiente: " + a.getNombre()));
            ambientes = AmbienteEJB.findAll();
        } else {
            context.addMessage(null, new FacesMessage("Error", " al eliminar"));
        }

    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public List<Ambiente> getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(List<Ambiente> ambientes) {
        this.ambientes = ambientes;
    }

    public Ambiente getAmbienteSeleccionado() {
        return ambienteSeleccionado;
    }

    public void setAmbienteSeleccionado(Ambiente ambienteSeleccionado) {
        this.ambienteSeleccionado = ambienteSeleccionado;
    }

    public Ambiente getEliminarseleccionado() {
        return eliminarseleccionado;
    }

    public void setEliminarseleccionado(Ambiente eliminarseleccionado) {
        this.eliminarseleccionado = eliminarseleccionado;
    }

}
