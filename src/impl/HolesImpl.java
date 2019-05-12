package impl;

import services.Cell;
import services.Environment;
import services.Holes;
import services.Screen;

public class HolesImpl implements Holes { 
	protected int hgt;
	protected int wdt;
	protected int time;
	private Environment envi;
	private Cell nature;

	@Override
	public Environment getEnvi() {
		return envi;
	}

	@Override
	public int getHoleHgt() {
		return hgt;
	}

	@Override
	public int getHoleCol() {
		return wdt;
	}

	@Override
	public void init(Screen screen, int x, int y, int t) {
		this.hgt = y;
		this.wdt = x;
		this.envi = (Environment) screen; 
		this.time= t;
		this.nature=envi.CellNature(x, y);
	}

	@Override
	public int getTime() {
		return time;
	}

}
