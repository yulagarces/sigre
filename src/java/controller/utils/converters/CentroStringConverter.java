/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.utils.converters;

import controller.AdminUserBean;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import model.Centro;



/**
 *
 * @author fabian
 */
@FacesConverter(forClass = model.Centro.class, value = "CentroConvertidor")
public class CentroStringConverter implements Converter{
    

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        ValueExpression vex
                = context.getApplication().getExpressionFactory()
                        .createValueExpression(context.getELContext(),
                                "#{adminBean}", AdminUserBean.class);

        AdminUserBean apre = (AdminUserBean) vex.getValue(context.getELContext());
        return apre.getCentro(Integer.valueOf(value));

    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object cent) {

        try {
            return ((Centro) cent).getIdCentro().toString();
        } catch (NullPointerException e) {
            return "";
        }

    }



}
