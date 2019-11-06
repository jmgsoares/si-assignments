package pt.onept.mei.is1920.mybay.business.utility;

import pt.onept.mei.is1920.mybay.common.type.Item;
import pt.onept.mei.is1920.mybay.data.persistence.type.PersistenceItem;

public final class MapItem {
	private MapItem() { }

	public static Item MapPersistenceItemToItem(PersistenceItem persistenceItem) {
		return new Item()
				.setName(persistenceItem.getName())
				.setPrice(persistenceItem.getPrice())
				.setCategory(persistenceItem.getCategory())
				.setPublishDate(persistenceItem.getPublishDate())
				.setItemImageUrl(persistenceItem.getImageUrl())
				.setItemImageDeleteHash(persistenceItem.getImageDeleteHash())
				.setSeller(MapUser.MapPersistenceUserToUser(persistenceItem.getSeller()));
	}

	public static PersistenceItem MapItemToPersistenceItem(Item item) {
		return new PersistenceItem()
				.setName(item.getName())
				.setPrice(item.getPrice())
				.setCategory(item.getCategory())
				.setPublishDate(item.getPublishDate())
				.setImageUrl(item.getItemImageUrl())
				.setImageDeleteHash(item.getItemImageDeleteHash());
	}
}
