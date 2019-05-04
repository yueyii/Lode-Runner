package decorators;

import services.Cell;
import services.Environment;
import services.Holes;
import services.Screen;

public class HolesDecorator implements Holes {

	private final Holes delegate;
	
	protected HolesDecorator(Holes delegate) {
		this.delegate=delegate;
	}
	
	protected Holes getDelegate() {
		return this.delegate;
	}
	
		
	@Override
	public Environment getEnvi() {
		return getDelegate().getEnvi();
	}

	@Override
	public int getHoleHgt() {
		return getDelegate().getHoleHgt();
	}

	@Override
	public int getHoleCol() {
		return getDelegate().getHoleCol();
	}

	@Override
	public void init(Screen screen, int x, int y, int t) {
		getDelegate().init(screen, x, y, t);
	}

	@Override
	public int getTime() {
		return getDelegate().getTime();
	}

}
