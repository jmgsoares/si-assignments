package pt.onept.mei.is1920.mybay.common.contract;

import pt.onept.mei.is1920.mybay.common.basicType.Item;

import javax.ejb.Local;

@Local
public interface ItemEJBRemote extends Crudable<Item> {
}
