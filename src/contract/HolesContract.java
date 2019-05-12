package contract;

import contractsError.InvariantError;
import contractsError.PreconditionError;
import decorators.HolesDecorator;
import services.Cell;
import services.Holes;
import services.Screen;

public class HolesContract extends HolesDecorator {

	protected HolesContract(Holes delegate) {
		super(delegate);
	}

	public void checkInvariant() {
		if(getHoleHgt()<0 || getHoleHgt()>getEnvi().getHeight()) {
			throw new InvariantError("HolesContract ==> \\inv 0<getHoleHgt()<getEnvi().getHeight() ");	
		}
		 
		if(getHoleCol()<0 || getHoleCol()>getEnvi().getWidth()) {
			throw new InvariantError("HolesContract ==> \\inv 0<getHoleCol()<getEnvi().getWidth() ");	
		}
		
		if(getEnvi().CellNature(getHoleCol(), getHoleHgt())!=Cell.HOL) {
			throw new InvariantError("HolesContract ==> \\inv getNature()!=Hol ");	
		}
		
		if(getTime()<0 || getTime()>10) {
			throw new InvariantError("HolesContract ==> \\inv 0<=getTime()<16");		
		} 

	}
	
	@Override
	public void init(Screen screen, int x, int y, int t) {
		if(t<0 || t>10) {
			throw new PreconditionError("HolesContract init() ==> //pre : time>=0 ");	
		}
		
		getDelegate().init(screen, x, y, t);

		checkInvariant();
	}

}
