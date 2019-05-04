package decorators;

import services.Cell;
import services.EditableScreen;
import decorators.ScreenDecorator;

public class EditableScreenDecorator extends ScreenDecorator implements EditableScreen{

	public EditableScreenDecorator(EditableScreen delegate) {
		super(delegate);
	}

	@Override
	protected EditableScreen getDelegate() {
		return (EditableScreen) super.getDelegate();
	}

	@Override
	public boolean isPlayable() {
		return getDelegate().isPlayable();
	}

	@Override
	public void setNature(int x, int y, Cell cell) {
		getDelegate().setNature(x, y, cell);
	}
	
}
