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
import model.PeticionEquipo;
import model.Equipo;
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
import service.equipoReservaEJB;
import service.UserEJB;
import service.EquiposEJB;

/**
 *
 * @author santi
 */
@Named(value = "equiporeservaBean")
@ViewScoped
public class equipoReservaBean implements Serializable {

    /**
     * Creates a new instance of ReservaBean
     */
    private ScheduleModel equiporeservaModelo;

    private List<PeticionEquipo> scheduleList;
    private List<PeticionEquipo> reserva_equipos;
    private List<PeticionEquipo> filtro_reserva;

    private List<Equipo> equipos;
    private Equipo equipo;
    private Ambiente ambiente;
    private Date sysdate;
    private Date minDate;

    private Date fromDate;
    private Date toDate;
    
    private boolean isDeleteable = false;
    private ScheduleEvent newPeticionEquipo;
    private ScheduleEvent editPeticionEquipo;
    private int idUser;
    private Persona per;
    private Ambiente ambienteSeleccionado;
    private Equipo equipoSeleccionado;
    private List<PeticionEquipo> filtro;
    
    private int countid;
   

    @EJB // pone a dispocision Servicio
    equipoReservaEJB equiporeservaEJB;
    @EJB // pone a dispocision Servicio
    UserEJB userEJB;
    @EJB
    AmbientesEJB ambienteEJB;
    @EJB 
    EquiposEJB equipoEJB;

    public equipoReservaBean() {
    }

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        equiporeservaModelo = new DefaultScheduleModel();
        scheduleList = new ArrayList<>();
        reserva_equipos = new ArrayList<>();
        String idString = context.getExternalContext().getSessionMap().get("userId").toString();
        idUser = Integer.valueOf(idString);
        per = userEJB.getByIdUser(idUser);
        initListSchedule();
        sysdate = Calendar.getInstance().getTime();
        minDate = Calendar.getInstance().getTime();
        fromDate = Calendar.getInstance().getTime();
        toDate = Calendar.getInstance().getTime();

        equipos = equipoEJB.getAllAvailableEquipos();
        equipo = equipos.get(0);
    }

    private void initListSchedule() {
        
        scheduleList.clear();
        
        scheduleList = equiporeservaEJB.getAllScheduleById();
        
      
        
        for (PeticionEquipo p1 : scheduleList) {
            addEvent(p1);
        }
        countid = 1;

    }

    private void addEvent(PeticionEquipo res) {
        String perNom = res.getPersonaIdusuario() != null ? "Usuario: "+res.getPersonaIdusuario().getNombre(): "",
                pertel = res.getPersonaIdusuario() != null ? "Telefono: "+res.getPersonaIdusuario().getTelefono(): "",
                percen= res.getPersonaIdusuario() != null ?  "Equipo: "+res.getEquipoIdEquipo().getNombre():"",
                resfich = res.getFichaIdficha() != null ? "Ficha: "+res.getFichaIdficha().getNombreFicha():"";

         StringBuilder title = new StringBuilder(res.getEquipoIdEquipo().getNombre()).append("\n");

        StringBuilder desc = new StringBuilder(perNom);
        desc.append(resfich).append("\n");
        desc.append(res.getDisponibilidad() == 1 ? "Estado: Aprobada" : "Estado: No aprobada").append("\n");
        desc.append(pertel).append("\n");
        desc.append(percen).append("\n");

        DefaultScheduleEvent d = new DefaultScheduleEvent();

        d.setTitle(title.toString());
        d.setData(res);
        d.setDescription(desc.toString());
        d.setStartDate(getDateFormat(res.getFechaInicio(), res.getHoraInicio()));
        d.setEndDate(getDateFormat(res.getFechaFinal(), res.getHoraFin()));
        d.setEditable(res.getDisponibilidad() == 1);
        
        
        
        d.setStyleClass((res.getPlazoVencido()==1)?"color-blue":"color-red");
        equiporeservaModelo.addEvent(d);

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
        editPeticionEquipo = (ScheduleEvent) selectEvent.getObject();
        
        if(((PeticionEquipo)editPeticionEquipo.getData()).getPersonaIdusuario().getPersonaIdusuario() != idUser){
            isDeleteable = true;
        }else{
            isDeleteable = false;
        }
    }

    public void onDateSelect(SelectEvent selectEvent) {
        newPeticionEquipo = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
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

        for (PeticionEquipo peticioinequipo : equiporeservaEJB.getAllAvailableSchedules()) {

            LocalDate newFromJoda = getDate(fromDate);
            LocalDate newToJoda = getDate(toDate);
            LocalDate resFromJoda = getDate(peticioinequipo.getFechaInicio());
            LocalDate resToJoda = getDate(peticioinequipo.getFechaFinal());
            int idequi = peticioinequipo.getEquipoIdEquipo().getIdEquipo();

            if (idequi == equipo.getIdEquipo()) {
                if ((!newFromJoda.isBefore(resFromJoda) && !newFromJoda.isAfter(resToJoda))
                        || (!newToJoda.isBefore(resFromJoda) && !newToJoda.isAfter(resToJoda))) {
                    LocalTime endInterval = getTime(peticioinequipo.getHoraFin());
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
                PeticionEquipo p = new PeticionEquipo();
                p.setDisponibilidad(1);
                p.setEstado(" ");
                p.setFechaInicio(fromDate);
                p.setHoraInicio(fromDate);
                p.setFechaFinal(toDate);
                p.setHoraFin(toDate);
                p.setPersonaIdusuario(new Persona(idUser));
                p.setEquipoIdEquipo(equipo);
                p.setPlazoVencido(1);

                if (equiporeservaEJB.save(p)) {

                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Guardo con exito", null));
                    scheduleList = new ArrayList<>();

                    equiporeservaModelo.clear();
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

    public void delete(PeticionEquipo p) {
        FacesContext context = FacesContext.getCurrentInstance();

        if (equiporeservaEJB.delete(p)) {
            scheduleList.clear();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Reserva eliminada con exito", null));

            initListSchedule();

        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar", null));
        }

    }

    public ScheduleModel getEquiporeservaModelo() {
        return equiporeservaModelo;
    }

    public void setEquiporeservaModelo(ScheduleModel equiporeservaModelo) {
        this.equiporeservaModelo = equiporeservaModelo;
    }

    public List<PeticionEquipo> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<PeticionEquipo> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    public Equipo getEquipo() {
        return equipo;
    }
    public Equipo getEquipo(Integer id) {
        return equipoEJB.getUserById(id);
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public Date getSysdate() {
        return sysdate;
    }

    public void setSysdate(Date sysdate) {
        this.sysdate = sysdate;
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

    public ScheduleEvent getNewPeticionEquipo() {
        return newPeticionEquipo;
    }

    public void setNewPeticionEquipo(ScheduleEvent newPeticionEquipo) {
        this.newPeticionEquipo = newPeticionEquipo;
    }

    public ScheduleEvent getEditPeticionEquipo() {
        return editPeticionEquipo;
    }

    public void setEditPeticionEquipo(ScheduleEvent editPeticionEquipo) {
        this.editPeticionEquipo = editPeticionEquipo;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Persona getPer() {
        return per;
    }

    public void setPer(Persona per) {
        this.per = per;
    }

    public Ambiente getAmbienteSeleccionado() {
        return ambienteSeleccionado;
    }

    public void setAmbienteSeleccionado(Ambiente ambienteSeleccionado) {
        this.ambienteSeleccionado = ambienteSeleccionado;
    }

    public Equipo getEquipoSeleccionado() {
        return equipoSeleccionado;
    }

    public void setEquipoSeleccionado(Equipo equipoSeleccionado) {
        this.equipoSeleccionado = equipoSeleccionado;
    }

    public int getCountid() {
        return countid;
    }

    public void setCountid(int countid) {
        this.countid = countid;
    }

    public equipoReservaEJB getEquiporeservaEJB() {
        return equiporeservaEJB;
    }

    public void setEquiporeservaEJB(equipoReservaEJB equiporeservaEJB) {
        this.equiporeservaEJB = equiporeservaEJB;
    }

    public UserEJB getUserEJB() {
        return userEJB;
    }

    public void setUserEJB(UserEJB userEJB) {
        this.userEJB = userEJB;
    }

    public List<PeticionEquipo> getReserva_equipos() {
        return reserva_equipos;
    }

    public void setReserva_equipos(List<PeticionEquipo> reserva_equipos) {
        this.reserva_equipos = reserva_equipos;
    }

    public List<PeticionEquipo> getFiltro_reserva() {
        return filtro_reserva;
    }

    public void setFiltro_reserva(List<PeticionEquipo> filtro_reserva) {
        this.filtro_reserva = filtro_reserva;
    }

    public List<PeticionEquipo> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<PeticionEquipo> filtro) {
        this.filtro = filtro;
    }

    public AmbientesEJB getAmbienteEJB() {
        return ambienteEJB;
    }

    public void setAmbienteEJB(AmbientesEJB ambienteEJB) {
        this.ambienteEJB = ambienteEJB;
    }

    public EquiposEJB getEquipoEJB() {
        return equipoEJB;
    }

    public void setEquipoEJB(EquiposEJB equipoEJB) {
        this.equipoEJB = equipoEJB;
    } 

    public boolean isIsDeleteable() {
        return isDeleteable;
    }

    public void setIsDeleteable(boolean isDeleteable) {
        this.isDeleteable = isDeleteable;
    }
    
    
}

