/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import model.Centro;
import model.Modulo;
import model.Persona;
import model.Usuario;
import model.dto.PersonaUsuarioDTO;
import model.dto.UsuarioPersonaDTO;
import org.primefaces.event.DragDropEvent;
import org.primefaces.model.DualListModel;
import service.ModuloEJB;
import service.UserEJB;
import service.utils.exception.BusinessAppException;
import service.utils.security.utils.UtilsEncrypt;

/**
 *
 * @author santi
 */
@Named(value = "adminBean")
@ViewScoped
public class AdminUserBean extends GeneralBean implements Serializable {

    private Usuario newUser;
    private String pass;
    private String fullNameUser;
    private List<Modulo> availableModules;
    private DualListModel<Modulo> modules;
    private List<Usuario> listUsers;
    private List<Persona> listPersonas;
    private List<Persona> filtro;

    public List<Persona> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<Persona> filtro) {
        this.filtro = filtro;
    }
    

    private List<PersonaUsuarioDTO> listUsersPer;
    private PersonaUsuarioDTO perUserSelect;
    private PersonaUsuarioDTO PerSelect;

    private Usuario userModule;

    private Persona newPer;
    private Centro centroSeleccionado;
    private List<Centro> centros;
    
    private Integer integer;

    @EJB // pone a dispocision Servicio
    UserEJB userEJB;

    @Inject // pone a dispocision Servicio
    ModuloEJB moduloEJB;

    public AdminUserBean() {
    }

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            setPermisos(context.getExternalContext().getSessionMap().get("rol").toString());
            String idString = context.getExternalContext().getSessionMap().get("userId").toString();
            newUser = new Usuario();
            newPer = new Persona();
            centroSeleccionado = new Centro();
            centros = userEJB.listarCentros();
            fullNameUser = userEJB.getFullName(Integer.parseInt(idString));
            listUsers = userEJB.getAllActiveUsers();
            listUsersPer = userEJB.getAllPersonUsers().size() > 0 ? userEJB.getAllPersonUsers() : new ArrayList<>();
            userModule = listUsers.size() > 0 ? listUsers.get(0) : new Usuario();
            initPickList(userModule.getModuloList() != null ? userModule.getModuloList(): new ArrayList<>());

        } catch (BusinessAppException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Acceso denegado contacte al administrador", null));
            navigate("/views/error/not-access.xhtml");
        }
    }

    private void initPickList(List<Modulo> moduleTarget) {
        availableModules = moduloEJB.getAllAvailableModules();
        availableModules.removeAll(new HashSet(moduleTarget));
        modules = new DualListModel<>(availableModules, moduleTarget);
    }

    public void createUser() {
        this.newUser.setPassword(UtilsEncrypt.getInstance().encryptPassword(pass));
        UsuarioPersonaDTO dto = new UsuarioPersonaDTO();

        dto.setUsuarios(newUser);
        dto.setPersonas(newPer);
        dto.setCentros(centroSeleccionado);

        FacesContext context = FacesContext.getCurrentInstance();

        if (userEJB.createUser(dto) > 0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Creacion correcta", null));
            newUser = new Usuario();
            newPer = new Persona();
           
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "duplicado", null));
        }
    }

    public Centro getCentro(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("no id provided");
        }
        for (Centro c : centros) {
            if (id.equals(c.getIdCentro())) {
                return c;
            }
        }
        return null;
    }

    public void cleanUser(UsuarioPersonaDTO dto) {
        dto.setPersonas(new Persona());
        dto.setUsuarios(new Usuario());
        dto.setCentros(new Centro());
    }

    public void assignModules() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (userEJB.assignModules(this.userModule, this.modules)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modulos asignados exitosamente a: " + this.userModule.getNombreCompleto(), null));
            initPickList(userEJB.getUserById(this.userModule.getIdusuario()).getModuloList());
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se agrego ningún módulo", null));
            initPickList(userEJB.getUserById(this.userModule.getIdusuario()).getModuloList());
        }

    }

    public void updatePersona() {

        FacesContext context = FacesContext.getCurrentInstance();
        if (userEJB.updatePersona(perUserSelect) == 1) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualización correcta ", null));

        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error al actualizar", null));

        }

    }

    public void deletePersona(PersonaUsuarioDTO p) {
        FacesContext context = FacesContext.getCurrentInstance();
     
        if (userEJB.deletePersona(p) == 1) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminación correcta ", null));
            listUsersPer.clear();
            listUsersPer = userEJB.getAllPersonUsers().size() > 0 ? userEJB.getAllPersonUsers() : new ArrayList<>();
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error al eliminar", null));

        }

    }

    public void changeUserSelection(ValueChangeEvent evt) {
        Usuario u = (Usuario) evt.getNewValue();
        initPickList(u.getModuloList());
    }

    public Usuario getUser(Integer id) {
        return userEJB.getUserById(id);
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getFullNameUser() {
        return fullNameUser;
    }

    public void setFullNameUser(String fullNameUser) {
        this.fullNameUser = fullNameUser;
    }

    public Usuario getNewUser() {
        return newUser;
    }

    public void setNewUser(Usuario newUser) {
        this.newUser = newUser;
    }

    public List<Modulo> getAvailableModules() {
        return availableModules;
    }

    public void setAvailableModules(List<Modulo> availableModules) {
        this.availableModules = availableModules;
    }

    public DualListModel<Modulo> getModules() {
        return modules;
    }

    public void setModules(DualListModel<Modulo> modules) {
        this.modules = modules;
    }

    public Usuario getUserModule() {
        return userModule;
    }

    public void setUserModule(Usuario userModule) {
        this.userModule = userModule;
    }

    public Persona getNewPer() {
        return newPer;
    }

    public void setNewPer(Persona newPer) {
        this.newPer = newPer;
    }

    public Centro getCentroSeleccionado() {
        return centroSeleccionado;
    }

    public void setCentroSeleccionado(Centro centroSeleccionado) {
        this.centroSeleccionado = centroSeleccionado;
    }

    public List<Centro> getCentros() {
        return centros;
    }

    public void setCentros(List<Centro> centros) {
        this.centros = centros;
    }

    public List<Usuario> getListUsers() {
        return listUsers;
    }

    public void setListUsers(List<Usuario> listUsers) {
        this.listUsers = listUsers;
    }

    public PersonaUsuarioDTO getPerUserSelect() {
        return perUserSelect;
    }

    public void setPerUserSelect(PersonaUsuarioDTO perUserSelect) {
        this.perUserSelect = perUserSelect;
    }

    public List<PersonaUsuarioDTO> getListUsersPer() {
        return listUsersPer;
    }

    public void setListUsersPer(List<PersonaUsuarioDTO> listUsersPer) {
        this.listUsersPer = listUsersPer;
    }

    public PersonaUsuarioDTO getPerSelect() {
        return PerSelect;
    }

    public void setPerSelect(PersonaUsuarioDTO PerSelect) {
        this.PerSelect = PerSelect;
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }
    
    

}
