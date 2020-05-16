/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import service.utils.exception.BusinessAppException;

/**
 *
 * @author santi
 */
public class GeneralBean {

    public void navigate(String newPage) {
        FacesContext fc = FacesContext.getCurrentInstance();
        String contextPath = fc.getExternalContext().getRequestContextPath();
        String beforePage = ((HttpServletRequest) fc.getExternalContext().getRequest()).getRequestURI();

        try {

            fc.getExternalContext().redirect(contextPath + newPage);

        } catch (IOException ex) {
            fc.addMessage(null, new FacesMessage("Error", "error de navegacion"));
        }
    }

    public void setPermisos(String rol) throws BusinessAppException {
            if(!rol.equals("ADMIN")){
                throw new BusinessAppException("1002", "Acceso no permitido");
            }
    }
}
