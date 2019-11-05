package pt.onept.mei.is1920.mybay.common.utility;

import pt.onept.mei.is1920.mybay.common.enums.ItemCategory;

import javax.persistence.AttributeConverter;

public class ItemCategoryConverter implements AttributeConverter<ItemCategory, String> {

	@Override
	public String convertToDatabaseColumn(ItemCategory itemCategory) {
		switch (itemCategory) {
			case INFORMATICS:
				return "informatics";
			case KITCHEN:
				return "kitchen";
			case GARDEN:
				return "garden";
			case CARS:
				return "cars";
			case WC:
				return "wc";
			default:
				return null;
		}
	}

	@Override
	public ItemCategory convertToEntityAttribute(String s) {
		if (s.equals("informatics")) return ItemCategory.INFORMATICS;
		if (s.equals("kitchen")) return ItemCategory.KITCHEN;
		if (s.equals("garden")) return ItemCategory.GARDEN;
		if (s.equals("cars")) return ItemCategory.CARS;
		if (s.equals("wc")) return ItemCategory.WC;
		else return null;
	}
}
