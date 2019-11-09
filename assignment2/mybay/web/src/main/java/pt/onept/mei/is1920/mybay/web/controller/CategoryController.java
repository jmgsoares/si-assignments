package pt.onept.mei.is1920.mybay.web.controller;

import lombok.Getter;
import lombok.Setter;
import pt.onept.mei.is1920.mybay.common.enums.ItemCategory;
import pt.onept.mei.is1920.mybay.common.converter.ItemCategoryConverter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named(value = "categoryController")
@RequestScoped
@Getter
@Setter
public class CategoryController {

	public List<String> getCategories() {
		List<String> categories = new ArrayList<>();
		for (ItemCategory c : ItemCategory.values())
			categories.add(ItemCategoryConverter.ItemCategoryToString(c));
		return categories;
	}
}
