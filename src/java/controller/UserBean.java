/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import model.Persona;
import model.Usuario;
import model.dto.PersonaUsuarioDTO;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import service.UserEJB;
import service.utils.exception.BusinessAppException;
import service.utils.security.utils.UtilsEncrypt;

/**
 *
 * @author santi
 */
@Named(value = "userBean")
@ViewScoped
public class UserBean extends GeneralBean implements Serializable {

    private Usuario usuario;
    private Persona persona;
    private String username;
    private String pass;
    private String passConfirm;
    private String oldPass;
    private UploadedFile uploadedPicture;
    private boolean hasImage;
    private Date lastModified;
    private String fullNameUser;
    private String newPass;

    @EJB // pone a dispocision Servicio
    UserEJB userEJB;

    public UserBean() {
    }

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        String idString = context.getExternalContext().getSessionMap().get("userId").toString();

        usuario = userEJB.getUserById(Integer.parseInt(idString));
        /*Prueba 1*/
        persona = userEJB.getPerById(Integer.parseInt(idString));
        /*Fin prueba*/

        hasImage = false;
        lastModified = new Date();
//        LoginBean login = (LoginBean) context.getApplication().getVariableResolver().resolveVariable(context, "loginBean");
        fullNameUser = userEJB.getFullName(usuario.getIdusuario());
    }

    public void fileUploadListener(FileUploadEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        // Get uploaded file from the FileUploadEvent
        this.uploadedPicture = e.getFile();
        byte[] content;
        try {
            content = IOUtils.toByteArray(uploadedPicture.getInputstream());
            if (userEJB.updateImage(content, usuario.getIdusuario())) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Imagen subida con exito", null));
                hasImage = true;
                lastModified = Calendar.getInstance().getTime();
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo actualizar su foto, contacte al administrador", null));
            }
        } catch (IOException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error del servidor, contacte al administrador", null));
        }
    }

    public void updateBasicData() {
        FacesContext context = FacesContext.getCurrentInstance();
        usuario.setPassword(pass);
        PersonaUsuarioDTO dto = new PersonaUsuarioDTO();

        dto.setUsuario(usuario);
        dto.setPersona(persona);

        if (userEJB.updatePersona(dto) == 1) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualización correcta ", null));

        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error del servidor, contacte al administrador", null));

        }

    }

    public void updatePassword() {
        FacesContext context = FacesContext.getCurrentInstance();

        String oldCon = UtilsEncrypt.getInstance().encryptPassword(this.oldPass);

        if (UtilsEncrypt.getInstance().comparePassword(oldCon, this.usuario.getPassword())) {
            if (!this.pass.equals("") && this.pass != null) {
                if (userEJB.updatePass(pass, this.usuario.getIdusuario())) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualización exitosa", null));
                    navigate("/views/logOn.xhtml");
                    ExternalContext c = FacesContext.getCurrentInstance().getExternalContext();
                    c.invalidateSession();
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al actualizar, contacte al administrador", null));
                }
            }
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contraseña Anterior no coincide", null));
        }

    }

    public int contarUsuarios() {
        return userEJB.countUsers();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPassConfirm() {
        return passConfirm;
    }

    public void setPassConfirm(String passConfirm) {
        this.passConfirm = passConfirm;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public UploadedFile getUploadedPicture() {
        return uploadedPicture;
    }

    public void setUploadedPicture(UploadedFile uploadedPicture) {
        this.uploadedPicture = uploadedPicture;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getFullNameUser() {
        return fullNameUser;
    }

    public void setFullNameUser(String fullNameUser) {
        this.fullNameUser = fullNameUser;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    //-----------
    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

}
