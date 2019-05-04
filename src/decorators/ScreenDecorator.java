package decorators;

import services.Cell;
import services.Screen;

public class ScreenDecorator implements Screen{
	
	private final Screen delegate;
		
	protected ScreenDecorator(Screen delegate) {
		this.delegate = delegate;
	}
	
	protected Screen getDelegate() {
		return delegate;
	}
	@Override
	public int getHeight() {
		return getDelegate().getHeight();
	}

	@Override
	public int getWidth() {
		return getDelegate().getHeight();
	}

	@Override
	public Cell CellNature(int x, int y) {
		return getDelegate().CellNature(x, y);
	}

	@Override
	public void Init(int h, int w) {
		getDelegate().Init(h, w);
	}

	@Override
	public void Dig(int x, int y) {
		getDelegate().Dig(x, y);
	}

	@Override
	public void Fill(int x, int y) {
		getDelegate().Fill(x, y);
	}


}
