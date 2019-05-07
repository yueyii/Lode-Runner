package impl;

import services.Cell;
import services.Screen;

public class ScreenImpl implements Screen {
	private int height;
	private int width;
	protected Cell cellList[][]; 
		
	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public Cell CellNature(int x, int y) {
		return cellList[x][y]; 
	}

	@Override
	public void Init(int x, int y) {
		width=x;
		height=y;
		cellList = new Cell[width][height];
		for (int i = 0; i < width ; i++) {
			for (int j = 0; j < height; j++) {
				cellList[i][j] = Cell.EMP;
			}
		}
 
	}

	@Override
	public void Dig(int x, int y) {
		this.cellList[x][y] = Cell.HOL; 
	}

	@Override
	public void Fill(int x, int y) {
		cellList[x][y] = Cell.PLT;
	}

}
