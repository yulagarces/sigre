/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.utils.converters;

import controller.equipoReservaBean;
import controller.ReservaBean;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.Converter;
import model.Equipo;


/**
 *
 * @author danny
 */
@FacesConverter(forClass = model.Equipo.class, value = "equipoConverter")
public class EquipoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        ValueExpression vex
                = context.getApplication().getExpressionFactory()
                        .createValueExpression(context.getELContext(),
                                "#{equiporeservaBean}", equipoReservaBean.class);

        equipoReservaBean adBean = (equipoReservaBean) vex.getValue(context.getELContext());
        return adBean.getEquipo(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
        try {
            return ((Equipo) object).getIdEquipo().toString();
        } catch (NullPointerException e) {
            return "";
        }
    }

}
