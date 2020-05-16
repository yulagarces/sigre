/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.utils.converters;

import controller.ReservaBean;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.Converter;
import model.Ambiente;

/**
 *
 * @author danny
 */
@FacesConverter(forClass = model.Ambiente.class, value = "ambienteConverter")
public class AmbienteConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        ValueExpression vex
                = context.getApplication().getExpressionFactory()
                        .createValueExpression(context.getELContext(),
                                "#{reservaBean}", ReservaBean.class);

        ReservaBean adBean = (ReservaBean) vex.getValue(context.getELContext());
        return adBean.getAmbiente(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
        try {
            return ((Ambiente) object).getIdAmbiente().toString();
        } catch (NullPointerException e) {
            return "";
        }
    }

}
