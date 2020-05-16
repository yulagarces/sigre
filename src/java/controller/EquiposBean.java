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
import javax.inject.Inject;
import model.Equipo;
import service.EquiposEJB;
import model.Ambiente;
import model.dto.EquiposDTO;
import service.AmbientesEJB;

/**
 *
 * @author santi
 */
@Named(value = "equiposBean")
@ViewScoped
public class EquiposBean implements Serializable {

    private Equipo equipo;
    private List<Equipo> equipos;
    private Equipo equipoSeleccionado;
    
    private List<Ambiente> ambientes;
    private Ambiente ambiente;

    private List<Equipo> filtro;
    
    @Inject
    @EJB // pone a dispocision Servicio
    EquiposEJB EquiposEJB;
    
    @EJB
    AmbientesEJB ambienteEJB;

    public EquiposBean() {
    }

    @PostConstruct

    public void inicio() {
        equipo = new Equipo();
        equipos = initListEquipo();
        ambientes = ambienteEJB.getAllAvailableAmbientes();
        ambiente = ambientes.get(0);
    }

    public List<Equipo> initListEquipo() {

        return EquiposEJB.findAll();

    }

    public int contarEquipo() {

        return EquiposEJB.countEquipo();
    }

    public void createEquipo() {
        
        EquiposDTO dto = new EquiposDTO();
        dto.setAmbiente(ambiente);
        dto.setEquipo(equipo);

        FacesContext context = FacesContext.getCurrentInstance();

        try {
            EquiposEJB.createEquipo(dto);
            equipos.clear();
            context.addMessage(null, new FacesMessage("Registrado", "Correctamente el equipo: " + equipo.getNombre().toUpperCase()));

            equipos = EquiposEJB.findAll();

            equipo = new Equipo();

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("Error al registrar", e.getMessage()));
        }
    }

    public void updateEquipo(Equipo e) {

        FacesContext context = FacesContext.getCurrentInstance();

        if (EquiposEJB.updateEquipo(e).getIdEquipo() != null) {
            context.addMessage(null, new FacesMessage("", "Actualización correcta del equipo: " + e.getNombre().toUpperCase()));
        } else {
            context.addMessage(null, new FacesMessage("Error", " al actualizar"));
        }

    }

//    public void deleteEquipo(Equipo e) {
//
//         FacesContext context = FacesContext.getCurrentInstance();
//
//        System.out.println("-----> equipo " + e.getNombre());
//        if (EquiposEJB.deleteEquipo(e)) {
//            equipos.clear();
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado Correctamente " + e.getNombre(), null));
//            equipos = EquiposEJB.findAll();
//        } else {
//            context.addMessage(null, new FacesMessage("Error", "Fallo al eliminar"));
//        }
//
//    }
    
    public void deleteEquipo(Equipo e) {

        FacesContext context = FacesContext.getCurrentInstance();

        if (EquiposEJB.deleteEquipo(e)) {
            equipos.clear();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Eliminación correcta del equipo: " + e.getNombre(),null));
            equipos = EquiposEJB.findAll();
        } else {
            context.addMessage(null, new FacesMessage("Error", " al eliminar"));
        }

    }

    
    public Ambiente getAmbiente(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("no id provided");
        }
        Ambiente temp = null;
        for (Ambiente a : ambientes) {
            if (id.equals(a.getIdAmbiente())) {
                temp = a;
                break;
            }
        }
        return temp;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    public Equipo getEquipoSeleccionado() {
        return equipoSeleccionado;
    }

    public void setEquipoSeleccionado(Equipo equipoSeleccionado) {
        this.equipoSeleccionado = equipoSeleccionado;
    }


    public List<Ambiente> getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(List<Ambiente> ambientes) {
        this.ambientes = ambientes;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public List<Equipo> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<Equipo> filtro) {
        this.filtro = filtro;
    }

    
}
