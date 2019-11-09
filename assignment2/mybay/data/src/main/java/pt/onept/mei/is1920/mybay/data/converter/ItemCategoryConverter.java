package pt.onept.mei.is1920.mybay.data.converter;

import pt.onept.mei.is1920.mybay.common.enums.ItemCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ItemCategoryConverter implements AttributeConverter<ItemCategory, String> {

	@Override
	public String convertToDatabaseColumn(ItemCategory ic) {
		return pt.onept.mei.is1920.mybay.common.converter.ItemCategoryConverter.ItemCategoryToString(ic);
	}

	@Override
	public ItemCategory convertToEntityAttribute(String s) {
		return pt.onept.mei.is1920.mybay.common.converter.ItemCategoryConverter.StringToItemCategory(s);
	}
}
