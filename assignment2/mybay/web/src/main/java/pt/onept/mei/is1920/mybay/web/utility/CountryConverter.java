package pt.onept.mei.is1920.mybay.web.utility;

import pt.onept.mei.is1920.mybay.common.enums.Country;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("countryConverter")
public class CountryConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
		return pt.onept.mei.is1920.mybay.common.converter.CountryConverter.StringToCountry(s);
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
		return pt.onept.mei.is1920.mybay.common.converter.CountryConverter.CountryToString((Country) o);
	}
}
