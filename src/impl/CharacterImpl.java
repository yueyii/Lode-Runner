package impl;
import services.Cell;
import services.Environment;
import services.Screen;
import services.Character;

public class CharacterImpl implements Character {
	protected int hgt;
	protected int wdt;
	private Environment envi;

	public CharacterImpl() {
		hgt=0;
		wdt=0;
	}

	public void init(Screen screen, int x, int y) {
		this.hgt = y;
		this.wdt = x;
		this.envi = (Environment) screen; 
	}

	public Environment getEnvi() {
		return envi;
	}

	public int getHgt() {
		return hgt;
	}
	public int getWdt() {
		return wdt;
	}

	public void goLeft() { 
		if (wdt!=0) {
			if(getEnvi().CellNature(getWdt()-1,getHgt())!=Cell.PLT 
					&& getEnvi().CellNature(getWdt()-1,getHgt())!=Cell.MTL) {

				if ((getEnvi().CellNature(getWdt(), getHgt())==Cell.LAD
						|| getEnvi().CellNature(getWdt(), getHgt())==Cell.HDR 
						|| (getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.PLT 
						||getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.MTL 
						||getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.LAD))

						|| (!getEnvi().getCellContent(getWdt(),getHgt()-1).isEmpty())) {
					wdt --;
				}

			}
		}
	}
	public void goRight() {
		if (wdt!=getEnvi().getWidth()-1) {
			if (getEnvi().CellNature(getWdt()+1,getHgt())!=Cell.MTL 
					&& getEnvi().CellNature(getWdt()+1,getHgt())!=Cell.PLT) {

				if ((getEnvi().CellNature(getWdt(),getHgt())==Cell.LAD 
						|| getEnvi().CellNature(getWdt(),getHgt())==Cell.HDR 
						|| (getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.PLT
						|| getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.MTL 
						|| getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.LAD))
						|| (!getEnvi().getCellContent(getWdt(),getHgt()-1).isEmpty())) {
					wdt++;
				}
			}
		}
	}
	public void goUp() {
		if (hgt!=(getEnvi().getHeight()-1)) {
			if (getEnvi().CellNature(getWdt(),getHgt()+1)!=Cell.MTL 
					&& getEnvi().CellNature(getWdt(),getHgt()+1)!=Cell.PLT) {
				if ((getEnvi().CellNature(getWdt(),getHgt())==Cell.LAD) && (getEnvi().CellNature(getWdt(),getHgt()+1)==Cell.EMP
						||getEnvi().CellNature(getWdt(),getHgt()+1)==Cell.LAD
						||getEnvi().CellNature(getWdt(),getHgt()+1)==Cell.HDR
						||getEnvi().CellNature(getWdt(),getHgt()+1)==Cell.HOL) ) {
					hgt++;
				}
			}
		}
	}

	public void goDown() {
		if (hgt!=0) {	
			if (getEnvi().CellNature(getWdt(),getHgt()-1)!=Cell.MTL 
					&& getEnvi().CellNature(getWdt(),getHgt()-1)!=Cell.PLT) {			
				if ((getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.EMP
						||getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.LAD
						||getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.HDR
						||getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.HOL) ) {
					hgt--;
				}
			}
		}
	}

}
