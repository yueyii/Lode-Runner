package decorators;

import java.util.ArrayList;

import services.Character;
import services.Environment;
import services.Item;
import services.ItemType;
import services.Screen;

public class ItemDecorator implements Item {

	private final Item delegate;
	
	protected ItemDecorator(Item delegate) {
		this.delegate=delegate;
	}
	
	protected Item getDelegate() {
		return this.delegate;
	}
	
	
	@Override
	public int getItemId() {
		return getDelegate().getItemId();
	}

	@Override
	public ItemType getNature() {
		return getDelegate().getNature();
	}

	@Override
	public int getItemHgt() {
		return getDelegate().getItemHgt();
	}

	@Override
	public int getItemCol() {
		return getDelegate().getItemCol();
	}

	@Override
	public Environment getEnvi() {
		return getDelegate().getEnvi();
	}

	@Override
	public void init(Screen screen, int x, int y, int id, ItemType nature) {
		getDelegate().init(screen, x, y, id, nature);
	}

	@Override
	public ArrayList<Integer> getItemIdList() {
		return getDelegate().getItemIdList();
	}

}
