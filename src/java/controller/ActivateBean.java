/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.utils.emails.EmailUtils;
import controller.utils.emails.JsfUtilities;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import model.Usuario;
import org.primefaces.context.RequestContext;
import service.UserEJB;

/**
 *
 * @author santi
 */
@Named(value = "activateBean")
@ViewScoped
public class ActivateBean extends GeneralBean implements Serializable {

    private Usuario usuario;
    private String email;
    private String username;
    private JsfUtilities jsfUtilities;
    private boolean isBlockUser;
    private String codActivate;
    private int id;

    @EJB // pone a dispocision Servicio
    UserEJB userEJB;

    @PostConstruct
    public void init() {
        jsfUtilities = JsfUtilities.getInstance();
        isBlockUser = true;
    }

    public void createCode() {
        String code = jsfUtilities.getCode();
        updateUserCod(code);
        email= userEJB.searchEmail(id);
        FacesContext context = FacesContext.getCurrentInstance();
        
            if (EmailUtils.getInstace().sendEmail(email, code, "Código de Activación")) {
                isBlockUser = false;
                RequestContext.getCurrentInstance().execute("PF('dlg2').show();");
            }
        

    }

    private void updateUserCod(String code) {
        Usuario u = userEJB.getUserbyName(this.username);
        this.id = u.getIdusuario();
        userEJB.updateCode(code, this.id);
    }

    public void createPass() {
        FacesContext context = FacesContext.getCurrentInstance();
        Usuario u = userEJB.getUserbyName(this.username);
        this.id = u.getIdusuario();
       email = userEJB.searchEmail(this.id);

            String code = jsfUtilities.getCode();
            if (EmailUtils.getInstace().sendEmail(email, code, "Nueva Contraseña")) {
                
                if (userEJB.updatePass(code, this.id)) {
                    navigate("/views/logOn.xhtml");
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo actualizar la contraseña, vuelva a intetarlo más tarde", null));
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El correo no se pudo enviar, vuelva a intentarlo más tarde", null));
            }
       
    }

    public void activateUser() {
         FacesContext context = FacesContext.getCurrentInstance();
        if(this.codActivate.equals(userEJB.getByCode(username))){
        userEJB.activateUserByCode(this.codActivate, this.id);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "en hora buenar"," codigo valido"));
            navigate("/views/logOn.xhtml");

        
        }else{
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error codigo no valido", null));
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public JsfUtilities getJsfUtilities() {
        return jsfUtilities;
    }

    public void setJsfUtilities(JsfUtilities jsfUtilities) {
        this.jsfUtilities = jsfUtilities;
    }

    public boolean isIsBlockUser() {
        return isBlockUser;
    }

    public void setIsBlockUser(boolean isBlockUser) {
        this.isBlockUser = isBlockUser;
    }

    public String getCodActivate() {
        return codActivate;
    }

    public void setCodActivate(String codActivate) {
        this.codActivate = codActivate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
