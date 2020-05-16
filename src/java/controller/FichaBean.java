/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import model.Ambiente;
import model.Ficha;
import model.Programa;
import model.Reserva;
import model.dto.FichaDTO;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import service.AmbientesEJB;
import service.FichaEJB;
import service.ReservaEJB;

/**
 *
 * @author santi
 */
@Named(value = "fichaBean")
@ViewScoped
public class FichaBean implements Serializable {

    private Ficha ficha;
    private List<Ficha> fichas;
    private Ficha fichaSeleccionado;
    private List<Programa> programas;
    private Programa progamaSeleccionado;

    private List<Ambiente> ambientes;
    private Ambiente ambiente;

    @Inject
    @EJB // pone a dispocision Servicio
    FichaEJB fichaEJB;

    @EJB
    AmbientesEJB ambienteEJB;

    @EJB
    ReservaEJB reservaEJB;

    /**
     * Creates a new instance of fichaBean
     */
    @PostConstruct
    public void init() {

        ficha = new Ficha();
        fichas = initListFicha();
        progamaSeleccionado = new Programa();
        programas = fichaEJB.listarProgramas();
        ficha = new Ficha();
        ambientes = ambienteEJB.getAllAvailableAmbientes();
        ambiente = ambientes.get(0);
        for (Programa programa : programas) {
            System.out.println("--->" + programa.getNombre());
        }

    }

    public List<Ficha> initListFicha() {

        return fichaEJB.findAll();

    }

    public FichaBean() {
    }

    private LocalDate getDate(Date date) {
        return new DateTime(date).toLocalDate();
    }

    private LocalTime getTime(Date date) {
        return LocalTime.fromDateFields(date);
    }

    public boolean validateRangeDate(FichaDTO dto) {

        for (Reserva reserva : reservaEJB.getAllAvailableSchedules()) {

            LocalDate newFromJoda = getDate(dto.getFicha().getFechaInicio());
            LocalDate newToJoda = getDate(dto.getFicha().getHoraFin());
            LocalDate resFromJoda = getDate(reserva.getFechaInicio());
            LocalDate resToJoda = getDate(reserva.getFechaFin());
            int idAmb = reserva.getAmbienteIdAmbiente().getIdAmbiente();

            if (idAmb == ambiente.getIdAmbiente()) {
                if ((!newFromJoda.isBefore(resFromJoda) && !newFromJoda.isAfter(resToJoda))
                        || (!newToJoda.isBefore(resFromJoda) && !newToJoda.isAfter(resToJoda))) {
                    LocalTime endInterval = getTime(reserva.getHoraFin());
                    LocalTime fromTime = getTime(dto.getFicha().getFechaInicio());
                    LocalTime toTime = getTime(dto.getFicha().getHoraFin());

                    if (endInterval.isAfter(fromTime) || endInterval.isAfter(toTime)) {
                        return false;
                    }
                }
            }

        }
        return true;
    }

    public void crearFicha() {

        FichaDTO dto = new FichaDTO();
        dto.setAmbiente(ambiente);
        dto.setFicha(ficha);
        dto.setPrograma(progamaSeleccionado);

        FacesContext context = FacesContext.getCurrentInstance();

        if ((dto.getFicha().getFechaFin().getYear() <= dto.getFicha().getFechaInicio().getYear() + 2) & dto.getFicha().getFechaFin().after(dto.getFicha().getFechaInicio())) {

            if (validateRangeDate(dto)) {

                if (fichaEJB.crearFicha(dto) > 0) {
                    fichaEJB.createReserva(dto);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La Creación de  ficha y asignación de ambiente se realizo con éxito", ""));

                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al crear. ", "Comuníquese con el administrador"));
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar. ", "Ya existe una reserva en ese horario"));
            }
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar datos.", " La fecha fin no puede ser menor o igual a la fecha de inicio y el rango maximo es de 2 años"));
        }

    }

    public Programa getPrograma(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("no id provided");
        }
        for (Programa p : programas) {
            if (id.equals(p.getIdPrograma())) {
                return p;
            }
        }
        return null;
    }

    public Ambiente getAmbiente(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("no id provided");
        }
        for (Ambiente a : ambientes) {
            if (id.equals(a.getIdAmbiente())) {
                return a;
            }
        }
        return null;
    }

    public void updateFicha(Ficha f) {

        FacesContext context = FacesContext.getCurrentInstance();

        if (fichaEJB.updateFicha(f).getIdFicha() != null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado Correctamente " + f.getIdFicha(), null));
        } else {
            context.addMessage(null, new FacesMessage("Error", " De Actualizacion"));
        }

    }

    public void deleteFicha(Ficha f) {

        FacesContext context = FacesContext.getCurrentInstance();

        System.out.println("-----> ficha " + f.getNombreFicha());
        if (fichaEJB.deleteFicha(f)) {
            fichas.clear();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado Correctamente " + f.getIdFicha(), null));
            fichas = fichaEJB.findAll();
        } else {
            context.addMessage(null, new FacesMessage("Error", "al eliminar"));
        }

    }

    public int contarFichas() {
        return fichaEJB.countFichas();
    }

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

    public List<Ficha> getFichas() {
        return fichas;
    }

    public void setFichas(List<Ficha> fichas) {
        this.fichas = fichas;
    }

    public Ficha getFichaSeleccionado() {
        return fichaSeleccionado;
    }

    public void setFichaSeleccionado(Ficha fichaSeleccionado) {
        this.fichaSeleccionado = fichaSeleccionado;
    }

    public FichaEJB getFichaEJB() {
        return fichaEJB;
    }

    public void setFichaEJB(FichaEJB FichaEJB) {
        this.fichaEJB = FichaEJB;

    }

    public Programa getProgamaSeleccionado() {
        return progamaSeleccionado;
    }

    public void setProgamaSeleccionado(Programa progamaSeleccionado) {
        this.progamaSeleccionado = progamaSeleccionado;
    }

    public List<Programa> getProgramas() {
        return programas;
    }

    public void setProgramas(List<Programa> programas) {
        this.programas = programas;
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

}
