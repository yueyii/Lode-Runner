package decorators;

import java.util.HashSet;

import services.Cell;
import services.CellContent;
import services.EditableScreen;
import services.Environment;
import services.Screen;
import decorators.ScreenDecorator;

public class EnvironmentDecorator extends ScreenDecorator implements Environment{

	public EnvironmentDecorator(Environment delegate) {
		super(delegate);
	}

	@Override
	protected Environment getDelegate() {
		return (Environment) super.getDelegate();
	}
	
	@Override
	public HashSet<CellContent> getCellContent(int x, int y) {
		return getDelegate().getCellContent(x, y);
	}

	@Override
	public void init(int x ,int y , EditableScreen edit) {
		 getDelegate().init( x, y , edit);
	}

	@Override
	public Cell CellNature(int x, int y) {
		return getDelegate().CellNature(x, y);
	}
}
