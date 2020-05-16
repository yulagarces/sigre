/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.utils.converters;

import controller.EquiposBean;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.Converter;
import model.Ambiente;
/**
 *
 * @author Alexis Banguero
 */
@FacesConverter(forClass = model.Ambiente.class, value = "ambienteEquipoConverter")
public class AmbienteEquipoConverter implements Converter {
    

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        ValueExpression vex
                = context.getApplication().getExpressionFactory()
                        .createValueExpression(context.getELContext(),
                                "#{equiposBean}", EquiposBean.class);

        EquiposBean adBean = (EquiposBean) vex.getValue(context.getELContext());
        return adBean.getAmbiente(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object ambi) {
        try {
            return ((Ambiente) ambi).getIdAmbiente().toString();
        } catch (NullPointerException e) {
            return "";
        }
    }

}
