package decorators;

import services.Character;
import services.Environment;
import services.Screen;

public class CharacterDecorator implements Character{
	
	private final Character delegate;
	
	protected CharacterDecorator(Character delegate) {
		this.delegate=delegate;
	}
	
	protected Character getDelegate() {
		return this.delegate;
	}
	
	@Override
	public Environment getEnvi() {
		return getDelegate().getEnvi();  
	}

	@Override
	public int getHgt() {
		return getDelegate().getHgt();
	}

	@Override
	public int getWdt() {
		return getDelegate().getWdt();
	}

	@Override
	public void init(Screen screen, int x, int y) {
		getDelegate().init(screen, x, y);
	}

	@Override
	public void goLeft() {
		getDelegate().goLeft();
	}

	@Override
	public void goRight() {
		getDelegate().goRight();
	}

	@Override
	public void goUp() {
		getDelegate().goUp();
	}

	@Override
	public void goDown() {
		getDelegate().goDown();
	}


}
