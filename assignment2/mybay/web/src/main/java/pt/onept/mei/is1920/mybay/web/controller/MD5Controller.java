package pt.onept.mei.is1920.mybay.web.controller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import pt.onept.mei.is1920.mybay.common.util.MD5;

@FacesConverter("pt.onept.mei.is1920.mybay.web.controller.MD5Controller")
public final class MD5Controller implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
		return MD5.GetMd5(s);
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
		return null;
	}
}
