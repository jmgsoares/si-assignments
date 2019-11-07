package pt.onept.mei.is1920.mybay.data.utility;

import pt.onept.mei.is1920.mybay.common.type.Item;
import pt.onept.mei.is1920.mybay.data.type.PersistenceItem;
import pt.onept.mei.is1920.mybay.data.type.PersistenceUser;

public final class MapItemUtility {
	private MapItemUtility() { }

	public static Item MapPersistenceItemToItem(PersistenceItem persistenceItem) {
		return new Item()
				.setId(persistenceItem.getId())
				.setName(persistenceItem.getName())
				.setPrice(persistenceItem.getPrice())
				.setCategory(persistenceItem.getCategory())
				.setPublishDate(persistenceItem.getPublishDate())
				.setPhotoUrl(persistenceItem.getImageUrl())
				.setPhotoDeleteHash(persistenceItem.getImageDeleteHash())
				.setSellerEmail(persistenceItem.getSeller().getEmail());
	}

	public static PersistenceItem MapItemToPersistenceItem(Item item) {
		return new PersistenceItem()
				.setId(item.getId())
				.setName(item.getName())
				.setPrice(item.getPrice())
				.setCategory(item.getCategory())
				.setSeller(new PersistenceUser().setEmail(item.getSellerEmail()))
				.setPublishDate(item.getPublishDate())
				.setImageUrl(item.getPhotoUrl())
				.setImageDeleteHash(item.getPhotoDeleteHash());
	}
}
