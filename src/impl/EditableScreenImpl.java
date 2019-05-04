package impl;

import services.Cell;
import services.EditableScreen;

public class EditableScreenImpl extends ScreenImpl 
implements EditableScreen {
	private boolean isPlayable=false;
	
	public EditableScreenImpl() {
	}
	
	@Override
	public void setNature(int x, int y, Cell cell) {
		super.cellList[x][y]=cell;
	}
	

	@Override
	public boolean isPlayable() {
		for (int i = 0; i < getWidth(); i++) {
			if(CellNature(i,0)==Cell.MTL) {
				isPlayable = true;	
			}
			else {
				break;
			}

			for (int j = 0; j < getHeight(); j++) {
				if( CellNature(i,j)!=Cell.HOL) {
					isPlayable = true;
				}
				else {
					break;
				}
			}
		}
		return isPlayable;
	}


}
