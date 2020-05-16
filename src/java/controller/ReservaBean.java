/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import model.Ambiente;
import model.Persona;
import model.Reserva;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import service.AmbientesEJB;
import service.ReservaEJB;
import service.UserEJB;

/**
 *
 * @author santi
 */
@Named(value = "reservaBean")
@ViewScoped
public class ReservaBean implements Serializable {

    /**
     * Creates a new instance of ReservaBean
     */
    private ScheduleModel reservaModelo;

    private List<Reserva> scheduleList;

    private List<Ambiente> ambientes;
    private Ambiente ambiente;
    private Date sysdate;
    private Date minDate;

    private Date fromDate;
    private Date toDate;

    private ScheduleEvent newReserva;
    private ScheduleEvent editReserva;
    private int idUser;
    private Persona per;
    private Ambiente ambienteSeleccionado;
    
    private int countid;
   

    @EJB // pone a dispocision Servicio
    ReservaEJB reservaEJB;
    @EJB // pone a dispocision Servicio
    UserEJB userEJB;

    @EJB
    AmbientesEJB ambienteEJB;

    public ReservaBean() {
    }

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        reservaModelo = new DefaultScheduleModel();

        String idString = context.getExternalContext().getSessionMap().get("userId").toString();
        idUser = Integer.valueOf(idString);
        per = userEJB.getByIdUser(idUser);
        initListSchedule();
        sysdate = Calendar.getInstance().getTime();
        minDate = Calendar.getInstance().getTime();
        fromDate = Calendar.getInstance().getTime();
        toDate = Calendar.getInstance().getTime();

        ambientes = ambienteEJB.getAllAvailableAmbientes();
        ambiente = ambientes.get(0);
    }

    private void initListSchedule() {

        scheduleList = reservaEJB.getAllScheduleById(per);
        for (Reserva r1 : scheduleList) {
            addEvent(r1);
        }
        countid = 1;

    }
    public void getByIdAmbiente() {
        
        scheduleList.clear();
       
        scheduleList = reservaEJB.getAllScheduleByIdAmbiente(ambienteSeleccionado);
        for (Reserva r1 : scheduleList) {
            //addEvent(r1);
            //AGREGAR CODIGO PARA MOSTRAR LA RESERVA DETALLA DEL EQUIPO
            System.out.println("si sirve");
        }
        
        countid = 2;

    }

    private void addEvent(Reserva res) {
        String perNom = res.getPersonaIdusuario() != null ? "Usuario: "+res.getPersonaIdusuario().getNombre(): "",
                pertel = res.getPersonaIdusuario() != null ? "Telefono: "+res.getPersonaIdusuario().getTelefono(): "",
                percen= res.getPersonaIdusuario() != null ?  "Centro: "+res.getPersonaIdusuario().getCentroIdCentro().getNombre():"",
                resfich = res.getFichaIdFicha() != null ? "Ficha: "+res.getFichaIdFicha().getNombreFicha():"";

        StringBuilder title = new StringBuilder(res.getAmbienteIdAmbiente().getNombre()).append("\n");

        StringBuilder desc = new StringBuilder(perNom);
        desc.append(resfich).append("\n");
        desc.append(res.getEstado() == 1 ? "Estado: Aprobada" : "Estado: No aprobada").append("\n");
        desc.append(pertel).append("\n");
        desc.append(percen).append("\n");

        DefaultScheduleEvent d = new DefaultScheduleEvent();

        d.setTitle(title.toString());
        d.setData(res);
        d.setDescription(desc.toString());
        d.setStartDate(getDateFormat(res.getFechaInicio(), res.getHoraInicio()));
        d.setEndDate(getDateFormat(res.getFechaFin(), res.getHoraFin()));
        d.setEditable(res.getEstado() == 1);
        reservaModelo.addEvent(d);

    }

    private Date getDateFormat(Date d, Date h) {
        Calendar resTime = GregorianCalendar.getInstance();
        resTime.setTime(d);
        resTime.set(Calendar.HOUR_OF_DAY, h.getHours());

        return resTime.getTime();
    }

    private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);

        return calendar;
    }

    public void onEventSelect(SelectEvent selectEvent) {
        editReserva = (ScheduleEvent) selectEvent.getObject();
    }

    public void onDateSelect(SelectEvent selectEvent) {
        newReserva = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
        fromDate = (Date) selectEvent.getObject();
        toDate = (Date) selectEvent.getObject();
        minDate = (Date) selectEvent.getObject();
    }

    private LocalDate getDate(Date date) {
        return new DateTime(date).toLocalDate();
    }

    private LocalTime getTime(Date date) {
        return LocalTime.fromDateFields(date);
    }

    public boolean validateRangeDate() {

        for (Reserva reserva : reservaEJB.getAllAvailableSchedules()) {

            LocalDate newFromJoda = getDate(fromDate);
            LocalDate newToJoda = getDate(toDate);
            LocalDate resFromJoda = getDate(reserva.getFechaInicio());
            LocalDate resToJoda = getDate(reserva.getFechaFin());
            int idAmb = reserva.getAmbienteIdAmbiente().getIdAmbiente();

            if (idAmb == ambiente.getIdAmbiente()) {
                if ((!newFromJoda.isBefore(resFromJoda) && !newFromJoda.isAfter(resToJoda))
                        || (!newToJoda.isBefore(resFromJoda) && !newToJoda.isAfter(resToJoda))) {
                    LocalTime endInterval = getTime(reserva.getHoraFin());
                    LocalTime fromTime = getTime(fromDate);
                    LocalTime toTime = getTime(toDate);

                    if (endInterval.isAfter(fromTime) || endInterval.isAfter(toTime)) {
                        return false;
                    }
                }
            }

        }
        return true;
    }

    public void create() {

        FacesContext context = FacesContext.getCurrentInstance();
        RequestContext.getCurrentInstance().execute("PF('reserveDialog').hide();");
        RequestContext.getCurrentInstance().update("scheduleLocale");

        if ((toDate.getYear()<=fromDate.getYear()+2) &&(fromDate.getYear() >= sysdate.getYear()) && (fromDate.getMonth() >= sysdate.getMonth()) && (fromDate.getDate() >= sysdate.getDate()) && (fromDate.getHours() >= 7 && toDate.getHours() <= 22) && toDate.after(fromDate)) {

            if (validateRangeDate()) {
                Reserva r = new Reserva();
                r.setEstado(1);
                r.setFechaInicio(fromDate);
                r.setHoraInicio(fromDate);
                r.setFechaFin(toDate);
                r.setHoraFin(toDate);
                r.setPersonaIdusuario(new Persona(idUser));
                r.setAmbienteIdAmbiente(ambiente);

                if (reservaEJB.save(r)) {

                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Guardo con exito", null));
                    scheduleList = new ArrayList<>();

                    reservaModelo.clear();
                    initListSchedule();
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al guardar", null));
                }
            } else {

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ya hay una reserva en ese horario", null));
            }
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar datos, recuerde no puede agregar fechas inferiores a las actuales y el horario de reservas es de 07:00 a 22:00", null));
        }
    }

    public void startDateFilter(SelectEvent event) {

        System.out.println("--->" + (Date) event.getObject());
        minDate = (Date) event.getObject();
        toDate = (Date) event.getObject();
    }

    public void delete(Reserva r) {
        FacesContext context = FacesContext.getCurrentInstance();

        if (reservaEJB.delete(r)) {
            scheduleList.clear();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Reserva eliminada con exito", null));

            initListSchedule();

        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar", null));
        }

    }

    public Ambiente getAmbiente(Integer id) {
        return ambienteEJB.getUserById(id);
    }

    public ScheduleModel getReservaModelo() {
        return reservaModelo;
    }

    public void setReservaModelo(ScheduleModel reservaModelo) {
        this.reservaModelo = reservaModelo;
    }

    public ScheduleEvent getNewReserva() {
        return newReserva;
    }

    public void setNewReserva(ScheduleEvent newReserva) {
        this.newReserva = newReserva;
    }

    public List<Reserva> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<Reserva> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public Date getSysdate() {
        return sysdate;
    }

    public void setSysdate(Date sysdate) {
        this.sysdate = sysdate;
    }

    public ScheduleEvent getEditReserva() {
        return editReserva;
    }

    public void setEditReserva(ScheduleEvent editReserva) {
        this.editReserva = editReserva;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    public Ambiente getAmbienteSeleccionado() {
        return ambienteSeleccionado;
    }

    public void setAmbienteSeleccionado(Ambiente ambienteSeleccionado) {
        this.ambienteSeleccionado = ambienteSeleccionado;
    }

    public int getCountid() {
        return countid;
    }

    public void setCountid(int countid) {
        this.countid = countid;
    }

    

}
