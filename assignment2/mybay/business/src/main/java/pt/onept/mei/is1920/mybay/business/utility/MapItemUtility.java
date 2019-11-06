package pt.onept.mei.is1920.mybay.business.utility;

import pt.onept.mei.is1920.mybay.common.type.Item;
import pt.onept.mei.is1920.mybay.data.persistence.type.PersistenceItem;

public final class MapItemUtility {
	private MapItemUtility() { }

	public static Item MapPersistenceItemToItem(PersistenceItem persistenceItem) {
		return new Item()
				.setId(persistenceItem.getId())
				.setName(persistenceItem.getName())
				.setPrice(persistenceItem.getPrice())
				.setCategory(persistenceItem.getCategory())
				.setPublishDate(persistenceItem.getPublishDate())
				.setItemImageUrl(persistenceItem.getImageUrl())
				.setItemImageDeleteHash(persistenceItem.getImageDeleteHash())
				.setSeller(MapUserUtility.MapPersistenceUserToUser(persistenceItem.getSeller()));
	}

	public static PersistenceItem MapItemToPersistenceItem(Item item) {
		return new PersistenceItem()
				.setId(item.getId())
				.setName(item.getName())
				.setPrice(item.getPrice())
				.setCategory(item.getCategory())
				.setPublishDate(item.getPublishDate())
				.setImageUrl(item.getItemImageUrl())
				.setImageDeleteHash(item.getItemImageDeleteHash());
	}
}
