/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.ejb.EJB;
import javax.inject.Named;
import org.omnifaces.cdi.GraphicImageBean;
import service.UserEJB;

/**
 *
 * @author santi
 */
@Named(value = "imageUserBean")
@GraphicImageBean
public class ImageUserBean {

    /**
     * Creates a new instance of ImageUserBean
     */
    public ImageUserBean() {
    }
    
    @EJB // pone a dispocision Servicio
    UserEJB userEJB;
    
    public byte[] getImage(int id) {
        return userEJB.getUserById(id).getImagen();
    }
    
}
