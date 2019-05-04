package contract;

import java.util.ArrayList;

import contractsError.InvariantError;
import contractsError.PreconditionError;
import decorators.ItemDecorator;
import services.Item;
import services.ItemType;
import services.Screen;

public class ItemContract extends ItemDecorator {

	public ItemContract(Item delegate) {
		super(delegate);
	}

	public void checkInvariant() {
		if(getItemHgt()<0 || getItemHgt()>getEnvi().getHeight()) {
			throw new InvariantError("ItemContract ==> \\inv 0<getItemHgt()<getEnvi().getHeight() ");	
		}
		 
		if(getItemCol()<0 || getItemCol()>getEnvi().getWidth()) {
			throw new InvariantError("ItemContract==> \\inv 0<getItemCol()<getEnvi().getWidth() ");	
		}
		
		if(getNature()!=ItemType.TREASURE) {
			throw new InvariantError("ItemContract==> \\getNature()==treasure ");	
		}
	}
	
	@Override
	public void init(Screen screen, int x, int y, int id, ItemType nature) {
		if(x< 0 || x> screen.getWidth()) {
			throw new PreconditionError("ItemContract init()==> //pre : 0<x<getWdith()");		
		}
		
		if(y< 0 || y> screen.getHeight()) {
			throw new PreconditionError("ItemContract init()==> //pre : 0<y<getHeight() ");		
		}
		
		if(id<0) {
			throw new PreconditionError("ItemContract init()==> //pre : id>=0 ");	
		}

//		//si id d'item existe d¨¦ja dans la liste d'item
//		if(!getItemIdList().isEmpty()) {
//			if (getItemIdList().contains(id)) {
//				System.out.println("itemsdsd"+id);
//				throw new PreconditionError("ItemContract init()==> //pre id d'item existe d¨¦ja");
//			}
//		}
		
		if(nature !=ItemType.TREASURE) {
			throw new InvariantError("ItemContract ==> \\getNature()==treasure ");	
		}
		
		getDelegate().init(screen, x, y, id, nature);
		checkInvariant();
	} 

	@Override
	public ArrayList<Integer> getItemIdList() {
		return getDelegate().getItemIdList();
	}
}
