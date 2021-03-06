package pt.onept.mei.is1920.mybay.web.utility;

import pt.onept.mei.is1920.mybay.common.enums.ItemCategory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("itemCategoryConverter")
public class ItemCategoryConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
		return pt.onept.mei.is1920.mybay.common.converter.ItemCategoryConverter.StringToItemCategory(s);
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
		return pt.onept.mei.is1920.mybay.common.converter.ItemCategoryConverter.ItemCategoryToString((ItemCategory) o);
	}
}
