/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.utils.converters;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import model.Modulo;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

/**
 *
 * @author danny
 */
@FacesConverter(forClass = model.Modulo.class, value = "moduloPickConverter")
public class ModuloPickConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        System.out.println("String value: {}" + value);
        return getObjectFromUIPickListComponent(component, value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
        String string;
        System.out.println("Object value: {}" + object);
        if (object == null) {
            string = "";
        } else {
            try {
                string = String.valueOf(((Modulo) object).getIdmodulo());
            } catch (ClassCastException cce) {
                throw new ConverterException();
            }
        }
        return string;
    }

    @SuppressWarnings("unchecked")
    private Modulo getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<Modulo> dualList;
        try {
            dualList = (DualListModel<Modulo>) ((PickList) component).getValue();
            Modulo m = getObjectFromList(dualList.getSource(), Integer.valueOf(value));
            if (m == null) {
                m = getObjectFromList(dualList.getTarget(), Integer.valueOf(value));
            }

            return m;
        } catch (ClassCastException cce) {
            throw new ConverterException("Error interno contacte al administrador");
        } catch (NumberFormatException nfe) {
            throw new ConverterException("Error interno contacte al administrador");
        }
    }

    private Modulo getObjectFromList(final List<?> list, final Integer identifier) {
        for (final Object object : list) {
            final Modulo m = (Modulo) object;
            if (m.getIdmodulo().equals(identifier)) {
                return m;
            }
        }
        return null;
    }

}
